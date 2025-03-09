package com.example.aio_bazar.ui.features.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.model.repository.product.ProductRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
   private val productRepository: ProductRepository
):ViewModel() {

     var productList= mutableStateOf<List<Product>>(listOf())

    fun loadDataByCategory(category:String){
        viewModelScope.launch {
        val productByCategoryFromLocal= productRepository.getAllProductByCategory(category = category)
            productList.value=productByCategoryFromLocal
        }
    }
}