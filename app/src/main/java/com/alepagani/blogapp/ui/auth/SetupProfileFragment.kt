package com.alepagani.blogapp.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alepagani.blogapp.core.Result
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alepagani.blogapp.R
import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.alepagani.blogapp.databinding.FragmentSetupProfileBinding
import com.alepagani.blogapp.domain.auth.AuthRepoImpl
import com.alepagani.blogapp.presentation.auth.AuthViewModel
import com.alepagani.blogapp.presentation.auth.AuthViewModelFactory

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    val REQUEST_IMAGE_CAPTURE = 1

    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(LoginDataSource())) }
    private var bitmap: Bitmap? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)

        binding.profileImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "No se encontro ninguna app para abrir la camara", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {
            val username = binding.profileUsername.text.toString().trim()
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploadin photo..").create()

            bitmap?.let {
                if (username.isNotEmpty()) {
                    viewModel.updateUserProfile(imageBitmap = bitmap!!, username = username).observe(viewLifecycleOwner, { result ->
                        when (result) {
                            is Result.Loading -> {
                                alertDialog.show()
                            }
                            is Result.Success -> {
                                alertDialog.dismiss()
                                findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                            }
                            is Result.Failure -> {
                                alertDialog.dismiss()
                            }
                        }

                    })
                } else {
                    // ingresa el usuario
                }
            } // else, captura una imagen
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}