package com.firstdueapplication.models

data class ProductList(
    val limit: Int,
    val message: String,
    val offset: Int,
    val products: List<Product>,
    val success: Boolean,
    val total_products: Int
)