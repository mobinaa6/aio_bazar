package com.example.aio_bazar.model.repository.product

import com.example.aio_bazar.model.data.Ads
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.model.db.ProductDao
import com.example.aio_bazar.model.net.ApiService

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
    ) : ProductRepository {
    override suspend fun getAllProduct(isInternetConnected:Boolean): List<Product> {
        if(isInternetConnected){
            val productList=apiService.getAllProduct()
            if(productList.success){
                productDao.insertOrUpdate(productList.products)
                return productList.products
            }
        }else{
            return productDao.getAll()
        }

        return listOf()
    }

    override suspend fun getAllAds(isInternetConnected:Boolean): List<Ads> {
        if(isInternetConnected){
           val adsList= apiService.getAllAds()
            if(adsList.success)
                return  adsList.ads
        }
        return listOf()
    }

    override suspend fun getAllProductByCategory(category: String): List<Product> {
        return productDao.getAllByCategory(category)

    }

    override suspend fun getProductById(productId: String): Product {
        return productDao.getById(productId)

    }
}