package com.example.aio_bazar.model.repository.product

import com.example.aio_bazar.model.data.Ads
import com.example.aio_bazar.model.data.Product

interface ProductRepository {

    suspend fun getAllProduct(isInternetConnected:Boolean):List<Product>

    suspend fun getAllAds(isInternetConnected:Boolean):List<Ads>

    suspend fun getAllProductByCategory(category:String):List<Product>


    suspend fun getProductById(productId:String):Product


}