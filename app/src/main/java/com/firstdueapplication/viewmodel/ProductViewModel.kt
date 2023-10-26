package com.firstdueapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.models.Product
import com.firstdueapplication.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    public val productMutableList
        get() = productRepository.productMutableList

    public val animalMutableList
        get() = productRepository.animalMutableList

    fun getProductList() {
        viewModelScope.launch {
            productRepository.getProdcutList()
        }
    }

    fun getAnimalList() {
        viewModelScope.launch {
            productRepository.getAnimalList()
        }
    }

}