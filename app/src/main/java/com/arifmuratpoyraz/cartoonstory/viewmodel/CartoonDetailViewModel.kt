package com.arifmuratpoyraz.cartoonstory.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arifmuratpoyraz.cartoonstory.model.Cartoon
import com.arifmuratpoyraz.cartoonstory.service.CartoonAPIService
import com.arifmuratpoyraz.cartoonstory.util.Singleton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CartoonDetailViewModel : ViewModel() {

     var cartoon = MutableLiveData<Cartoon>()
    private val disposable = CompositeDisposable()
    private val cartoonAPIService = CartoonAPIService()
    private var cartoonId = Singleton.id

    fun getData(){
        val url = "${cartoonId}/info.0.json"
        disposable.add(cartoonAPIService.getData(url).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Cartoon>(){
                override fun onSuccess(t: Cartoon) {
                    cartoon.value = t
                    println(t)
                }
                override fun onError(e: Throwable) {
                    println(e)
                }
            }))
    }
}