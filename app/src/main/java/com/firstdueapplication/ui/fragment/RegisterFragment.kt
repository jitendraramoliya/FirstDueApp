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
import com.firstdueapplication.databinding.FragmentRegisterBinding
import com.firstdueapplication.models.UserRequest
import com.firstdueapplication.utils.Utils
import com.firstdueapplication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val authViewModel by viewModels<AuthViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSignUp.setOnClickListener {
            binding.btnLogin.requestFocus()
            Utils.hideKeyboard(requireContext(), binding.btnLogin)
            
            val validateUserInput = authViewModel.validateUserInput(getUserRequest(), false)
            if (validateUserInput.first) {
                binding.txtError.text = ""
                authViewModel.registerUser(getUserRequest())
            } else {
                binding.txtError.text = validateUserInput.second
            }
        }

        handleObserver()
    }

    private fun handleObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbProgressBar.isVisible = false
            when (it) {
                is NetworkResult.Error -> binding.txtError.text = it.message
                is NetworkResult.Loading -> {
                    binding.pbProgressBar.isVisible = true
                }
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_RegisterFragment_to_AnimalFragment)
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
        return UserRequest(
            binding.etUsername.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}