package com.example.aio_bazar.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProductResponse(
    val products: List<Product>,
    val success: Boolean
)

@Entity("product_table")
data class Product(
    @PrimaryKey
    val productId: String,
    val category: String,
    val detailText: String,
    val imgUrl: String,
    val material: String,
    val name: String,
    val price: String,
    val soldItem: String,
    val tags: String
)

