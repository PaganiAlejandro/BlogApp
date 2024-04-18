package com.alepagani.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alepagani.blogapp.R
import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.alepagani.blogapp.databinding.FragmentRegisterBinding
import com.alepagani.blogapp.domain.auth.AuthRepoImpl
import com.alepagani.blogapp.presentation.auth.AuthViewModel
import com.alepagani.blogapp.presentation.auth.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(LoginDataSource())) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            val username = binding.edittextUsername.text.toString().trim()
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            val passwordConfirm = binding.edittextConfirnPassword.text.toString().trim()

            if (validateUserData(password, passwordConfirm, username, email)) return@setOnClickListener

            createUser(email, password, username)
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }

                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateUserData(password: String, passwordConfirm: String, username: String, email: String): Boolean {
        if (password != passwordConfirm) {
            binding.edittextConfirnPassword.error = "Password does not match"
            return true
        }

        if (username.isEmpty()) {
            binding.edittextUsername.error = "Username is Empty"
            return true
        }

        if (email.isEmpty()) {
            binding.edittextEmail.error = "Email is Empty"
            return true
        }

        if (password.isEmpty()) {
            binding.edittextPassword.error = "Password is Empty"
            return true
        }

        if (passwordConfirm.isEmpty()) {
            binding.edittextConfirnPassword.error = "Password confirm is Empty"
            return true
        }
        return false
    }
}