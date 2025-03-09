package com.example.aio_bazar.model.repository.User

interface UserRepository {

  suspend fun signUp(name:String,username:String,password:String):String
  suspend fun signIn(username:String,password: String):String

   fun signOut()
   fun loadToken()


   fun saveToken(newToken:String)
   fun getToken():String?

   fun saveUserName(username: String)
   fun getUserName():String

}