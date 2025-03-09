package com.example.aio_bazar.ui.features.main

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aio_bazar.model.data.Ads
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.model.repository.product.ProductRepository
import com.example.aio_bazar.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainViewModel(
    private val productRepository: ProductRepository,
    isInternetConnected: Boolean


) : ViewModel() {
    val productList = mutableStateOf<List<Product>>(listOf())
    val adsList = mutableStateOf<List<Ads>>(listOf())
    val showProgress = mutableStateOf(false)

    init {
        refreshAllDataFromNet(isInternetConnected)
    }


    private fun refreshAllDataFromNet(isInternetConnected: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler) {
            if (isInternetConnected)
                showProgress.value = true
            val dataProducts = async { productRepository.getAllProduct(isInternetConnected) }
            val dataAds = async { productRepository.getAllAds(isInternetConnected) }
            updateData(dataProducts = dataProducts.await(), dataAds = dataAds.await())
                showProgress.value = false

        }

    }


    private fun updateData(dataProducts: List<Product>, dataAds: List<Ads>) {
        productList.value = dataProducts
        adsList.value = dataAds

    }


}