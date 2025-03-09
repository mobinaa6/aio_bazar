package com.example.aio_bazar.model.repository.comment

import com.example.aio_bazar.model.data.Comment
import com.example.aio_bazar.model.data.CommentResponse
import com.example.aio_bazar.model.net.ApiService
import com.google.gson.JsonObject

class CommentRepositoryImpl (
    private val apiService: ApiService
):CommentRepository{
    override suspend fun getComments(productId: String): List<Comment> {

        val jsonObject=JsonObject().apply {
            addProperty("productId",productId)
        }

        val commentList=apiService.getComments(jsonObject)

        return if(commentList.success){
            commentList.comments

        }else{
            listOf()
        }
    }

    override suspend fun addNewComment(
        productId: String,
        text: String,
        isSuccess: (String) -> Unit
    ) {
        val jsonObject=JsonObject().apply {
            addProperty("productId",productId)
            addProperty("text",text)
        }
        val result=apiService.AddNewComment(jsonObject)
        if(result.success){
            isSuccess.invoke(result.message)
        }else{
            isSuccess.invoke("comment not added")
        }

    }

}