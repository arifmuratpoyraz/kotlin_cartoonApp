package com.arifmuratpoyraz.cartoonstory.service

import com.arifmuratpoyraz.cartoonstory.model.Cartoon
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface CartoonAPI {
    @GET
    fun getCartoon(@Url url: String) : Single<Cartoon>
}