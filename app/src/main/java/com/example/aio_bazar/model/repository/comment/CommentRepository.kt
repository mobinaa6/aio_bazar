package com.example.aio_bazar.model.repository.comment

import com.example.aio_bazar.model.data.Comment
import com.example.aio_bazar.model.data.CommentResponse

interface CommentRepository {
    suspend fun getComments(productId:String):List<Comment>
    suspend fun  addNewComment(productId: String,text:String,isSuccess:(String)->Unit)
}