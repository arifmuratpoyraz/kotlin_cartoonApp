package com.arifmuratpoyraz.cartoonstory.service

import com.arifmuratpoyraz.cartoonstory.model.Cartoon
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CartoonAPIService {

    private val BASE_URL = "https://xkcd.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CartoonAPI::class.java)

    fun getData(url : String): Single<Cartoon> {
    return api.getCartoon(url)
    }
}
