package com.firstdueapplication.repository

import androidx.lifecycle.MutableLiveData
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.api.UserApi
import com.firstdueapplication.models.UserRequest
import com.noteapplication.models.User
import com.noteapplication.models.UserResponse
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class UserRepository @Inject constructor(){

    private var _userData = MutableLiveData<NetworkResult<UserResponse>>()
    public val userData
        get() = _userData

    public suspend fun SignIn(userRequest: UserRequest) {
        _userData.postValue(NetworkResult.Loading())
        val userResponse = UserResponse(
            Random(100).nextInt().toString(),
            User(1, userRequest.email, userRequest.username, System.currentTimeMillis().toString())
        )
        delay(1000)
        _userData.postValue(NetworkResult.Success(userResponse))
    }

    public suspend fun SignUp(userRequest: UserRequest) {
        _userData.postValue(NetworkResult.Loading())
        val userResponse = UserResponse(
            Random(100).nextInt().toString(),
            User(1, userRequest.email, userRequest.username, System.currentTimeMillis().toString())
        )
        delay(1000)
        _userData.postValue(NetworkResult.Success(userResponse))
    }

}