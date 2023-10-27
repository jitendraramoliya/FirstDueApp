package com.firstdueapplication.fragment

import com.firstdueapplication.models.UserRequest
import com.firstdueapplication.repository.UserRepository
import com.firstdueapplication.viewmodel.AuthViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class UserFlow {

    lateinit var userRepository: UserRepository
    lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        userRepository = UserRepository()
        authViewModel = AuthViewModel(userRepository)
    }

    @Test
    fun checkAuth_empty_username_expected_false() {
        val userRequest = UserRequest("", "jiten@gmail.com", "jiten")
        val result = authViewModel.validateUserInput(userRequest, false)
        Assert.assertEquals(false, result.first)
        Assert.assertEquals("Please enter username", result.second)
    }

    @Test
    fun checkAuth_empty_email_expected_false() {
        val userRequest = UserRequest("jiten", "", "jiten")
        val result = authViewModel.validateUserInput(userRequest, true)
        Assert.assertEquals(false, result.first)
        Assert.assertEquals("Please enter email", result.second)
    }

    @Test
    fun checkAuth_empty_password_expected_false() {
        val userRequest = UserRequest("jiten", "jiten@gmail.com", "")
        val result = authViewModel.validateUserInput(userRequest, false)
        Assert.assertEquals(false, result.first)
        Assert.assertEquals("Please enter password", result.second)
    }

    @Test
    fun checkAuth_invalid_email_expected_false() {
        val userRequest = UserRequest("jiten", "jiten@com", "jiten")
        val result = authViewModel.validateUserInput(userRequest, false)
        Assert.assertEquals(false, result.first)
        Assert.assertEquals("Please enter valid email", result.second)
    }

    @Test
    fun checkAuth_invalid_password_expected_false() {
        val userRequest = UserRequest("jiten", "jiten@gmail.com", "ji")
        val result = authViewModel.validateUserInput(userRequest, false)
        Assert.assertEquals(false, result.first)
        Assert.assertEquals(
            "Password should character length should be fall in 5 to 15",
            result.second
        )
    }

    @Test
    fun checkAuth_valid_user_expected_true() {
        val userRequest = UserRequest("jiten", "jiten@gmail.com", "jiten")
        val result = authViewModel.validateUserInput(userRequest, false)
        Assert.assertEquals(true, result.first)
        Assert.assertEquals("", result.second)
    }

}