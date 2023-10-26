package com.firstdueapplication.api

import com.firstdueapplication.models.AnimalList
import com.firstdueapplication.models.Product
import com.firstdueapplication.models.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("products?offset=0&limit=200")
    suspend fun getProductList():Response<ProductList>

    @GET("photos?offset=0&limit=200")
    suspend fun getAnimalList():Response<AnimalList>

}