package com.firstdueapplication.models

data class AnimalList(
    val limit: Int,
    val message: String,
    val offset: Int,
    val photos: List<Photo>,
    val success: Boolean,
    val total_photos: Int
)