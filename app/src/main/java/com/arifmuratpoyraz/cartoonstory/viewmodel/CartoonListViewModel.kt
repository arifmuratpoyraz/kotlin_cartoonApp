package com.arifmuratpoyraz.cartoonstory.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arifmuratpoyraz.cartoonstory.model.Cartoon
import com.arifmuratpoyraz.cartoonstory.service.CartoonAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class CartoonListViewModel : ViewModel() {
    var cartoons = MutableLiveData<Cartoon>()
    var errorMessage = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    private val cartoonAPIService = CartoonAPIService()
    private val disposable = CompositeDisposable()

    fun refreshData() {
        val randomPageNumber = (1..2850).random()
        val randomUrl = "${randomPageNumber}/info.0.json"
        loading.value = true

        disposable.add(cartoonAPIService.getData(randomUrl).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Cartoon>(){

                override fun onSuccess(t: Cartoon) {
                    cartoons.value = t
                    errorMessage.value = false
                    loading.value = false
                }

                override fun onError(e: Throwable) {
                    errorMessage.value = true
                    loading.value = false
                }
            }))
    }
}

