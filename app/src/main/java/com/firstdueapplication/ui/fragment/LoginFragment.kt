package com.firstdueapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.firstdueapplication.R
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.databinding.FragmentLoginBinding
import com.firstdueapplication.databinding.FragmentRegisterBinding
import com.firstdueapplication.models.UserRequest
import com.firstdueapplication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val authViewModel by viewModels<AuthViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }

        binding.btnLogin.setOnClickListener {
            val validateUserInput = authViewModel.validateUserInput(getUserRequest(), true)
            if (validateUserInput.first) {
                binding.tvError.text = ""
                authViewModel.loginUser(getUserRequest())
            } else {
                binding.tvError.text = validateUserInput.second
            }

        }

        handleObserver()
    }

    private fun handleObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbProgressBar.isVisible = false
            when (it) {
                is NetworkResult.Error -> binding.tvError.text = it.message
                is NetworkResult.Loading -> {
                    binding.pbProgressBar.isVisible = true
                }
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
        return UserRequest("", binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}