package com.example.aio_bazar.model.repository.User

import android.content.SharedPreferences
import com.example.aio_bazar.model.net.ApiService
import com.example.aio_bazar.model.repository.TokenInMemory
import com.example.aio_bazar.util.VALUE_SUCCESS
import com.google.gson.JsonObject

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
): UserRepository {
    override suspend fun signUp(name: String, username: String, password: String):String {

        val jsonObject=JsonObject().apply {
            addProperty("name",name)
            addProperty("email",username)
            addProperty("password",password)
        }
       val result= apiService.signUp(jsonObject)
        if (result.success){
            TokenInMemory.refreshToken(username,result.token)
            saveToken(result.token)
            saveUserName(username)
            return VALUE_SUCCESS
        }else{
            return result.message
        }


    }

    override suspend fun signIn(username: String, password: String):String {
        val jsonObject=JsonObject().apply {

            addProperty("email",username)
            addProperty("password",password)
        }
       val result= apiService.signIn(jsonObject)

        return if(result.success){
            TokenInMemory.refreshToken(username,result.token)
            saveToken(result.token)
            saveUserName(username)
            VALUE_SUCCESS
        }else{
            result.message
        }

    }

    override fun signOut() {
        TokenInMemory.refreshToken(null,null)
        sharedPreferences.edit().clear().apply()
    }

    override fun loadToken() {
        TokenInMemory.refreshToken(getUserName(),getToken())
    }

    override fun saveToken(newToken: String) {
        sharedPreferences.edit().putString("token",newToken).apply()
    }

    override fun getToken(): String? {
     return   sharedPreferences.getString("token",null)
    }

    override fun saveUserName(username: String) {
        sharedPreferences.edit().putString("username",username).apply()
    }

    override fun getUserName(): String {
        return   sharedPreferences.getString("username","")!!
    }
}