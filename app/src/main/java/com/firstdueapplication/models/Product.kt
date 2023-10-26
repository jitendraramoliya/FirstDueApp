package com.firstdueapplication.models

data class Product(
    val category: String,
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val photo_url: String,
    val price: Double,
    val updated_at: String
)