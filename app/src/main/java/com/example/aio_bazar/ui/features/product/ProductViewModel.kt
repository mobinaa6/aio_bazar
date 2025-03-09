package com.example.aio_bazar.ui.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aio_bazar.model.data.Comment
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.model.repository.comment.CommentRepository
import com.example.aio_bazar.model.repository.product.ProductRepository
import com.example.aio_bazar.util.EMPTY_PRODUCT
import com.example.aio_bazar.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository
) : ViewModel() {
    val productState = mutableStateOf<Product>(EMPTY_PRODUCT)
    val commentListState= mutableStateOf<List<Comment>>(listOf())

    fun loadData(productId: String,isInternetConnected:Boolean){
        loadProductFromLocal(productId = productId)
        if(isInternetConnected)
        getComments(productId)
    }

    private fun loadProductFromLocal(productId: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            productState.value = productRepository.getProductById(productId = productId)

        }
    }
    private fun getComments(productId: String){
        viewModelScope.launch (coroutineExceptionHandler){
            commentListState.value= commentRepository.getComments(productId = productId)
        }
    }

    fun addNewComment(productId: String,text:String,isSuccess:(String)->Unit){
        viewModelScope.launch (coroutineExceptionHandler){
            commentRepository.addNewComment(productId, text, isSuccess)
            getComments(productId)
        }
    }
}