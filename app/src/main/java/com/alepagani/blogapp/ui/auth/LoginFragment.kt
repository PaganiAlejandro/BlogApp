package com.alepagani.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alepagani.blogapp.R
import com.alepagani.blogapp.core.Resource
import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.alepagani.blogapp.databinding.FragmentLoginBinding
import com.alepagani.blogapp.domain.auth.LoginRepoImpl
import com.alepagani.blogapp.presentation.login.LoginScreenViewModel
import com.alepagani.blogapp.presentation.login.LoginScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<LoginScreenViewModel> { LoginScreenViewModelFactory(LoginRepoImpl(LoginDataSource())) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        } ?: run {
            doLogin()
        }
    }

    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            validateCredential(email, password)
            signIn(email, password)
        }
    }

    private fun validateCredential(email: String, password: String) {
        if (email.isEmpty()) {
            binding.edittextEmail.error = "E-mail is empty"
            return
        }
        if (password.isEmpty()) {
            binding.edittextEmail.error = "Password is empty"
            return
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}