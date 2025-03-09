package com.example.aio_bazar.ui.features.singUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aio_bazar.model.repository.User.UserRepository
import com.example.aio_bazar.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    fun signUpUser(loggingEvent:(String)->Unit) {
        viewModelScope.launch (coroutineExceptionHandler){
          val result=  userRepository.signUp(name.value!!,email.value!!, password.value!!)
            loggingEvent.invoke(result)
        }
    }
}