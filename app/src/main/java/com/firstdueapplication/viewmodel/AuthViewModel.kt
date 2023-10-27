package com.firstdueapplication.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.models.UserRequest
import com.firstdueapplication.repository.UserRepository
import com.noteapplication.models.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userData

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.SignUp(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.SignIn(userRequest)
        }
    }

    fun validateUserInput(userRequest: UserRequest, isLogin: Boolean): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (!isLogin && isEmpty(userRequest.username)) {
            result = Pair(false, "Please enter username")
        } else if (isEmpty(userRequest.email)) {
            result = Pair(false, "Please enter email")
        } else if (isEmpty(userRequest.password)) {
            result = Pair(false, "Please enter password")
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(userRequest.email).matches()) {
            result = Pair(false, "Please enter valid email")
        } else if (userRequest.password.length < 5 || userRequest.password.length > 15) {
            result = Pair(false, "Password should character length should be fall in 5 to 15")
        }

        return result
    }

    fun isEmpty(str: CharSequence):Boolean{
        return str == null || str.length == 0
    }

}