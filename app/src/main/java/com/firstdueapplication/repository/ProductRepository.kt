package com.firstdueapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.api.ProductApi
import com.firstdueapplication.models.Photo
import com.firstdueapplication.models.Product
import com.firstdueapplication.models.ProductList
import javax.inject.Inject


open class ProductRepository @Inject constructor(private val productApi: ProductApi) {

    private var _productMutableList = MutableLiveData<NetworkResult<List<Product>>>()
    public val productMutableList: LiveData<NetworkResult<List<Product>>>
        get() = _productMutableList

    private var _animalMutableList = MutableLiveData<NetworkResult<List<Photo>>>()
    public val animalMutableList: LiveData<NetworkResult<List<Photo>>>
        get() = _animalMutableList

    suspend fun getProdcutList() {
        _productMutableList.postValue(NetworkResult.Loading())
        val response = productApi.getProductList()
        if (response.isSuccessful && response.body() != null) {
            println(response.body().toString())
            _productMutableList.postValue(NetworkResult.Success(response.body()!!.products))
        } else if (response.errorBody() != null) {
            _productMutableList.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _productMutableList.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getAnimalList() {
        _animalMutableList.postValue(NetworkResult.Loading())
        val response = productApi.getAnimalList()
        if (response.isSuccessful && response.body() != null) {
            println(response.body().toString())
            _animalMutableList.postValue(NetworkResult.Success(response.body()!!.photos))
        } else if (response.errorBody() != null) {
            _animalMutableList.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _animalMutableList.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}