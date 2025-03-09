package com.example.aio_bazar.ui.features.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aio_bazar.model.repository.User.UserRepository
import com.example.aio_bazar.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun signInUser(loggingEvent:(String)->Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {
           val result= userRepository.signIn(email.value!!, password.value!!)
            loggingEvent.invoke(result)


        }

    }
}