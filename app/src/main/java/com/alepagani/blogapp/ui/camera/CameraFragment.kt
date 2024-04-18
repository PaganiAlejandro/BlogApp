package com.alepagani.blogapp.ui.camera

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alepagani.blogapp.R
import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.remote.camera.CameraDataSource
import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.alepagani.blogapp.databinding.FragmentCameraBinding
import com.alepagani.blogapp.databinding.FragmentSetupProfileBinding
import com.alepagani.blogapp.domain.auth.AuthRepoImpl
import com.alepagani.blogapp.domain.camera.CameraRepoImpl
import com.alepagani.blogapp.presentation.auth.AuthViewModel
import com.alepagani.blogapp.presentation.auth.AuthViewModelFactory
import com.alepagani.blogapp.presentation.camera.CameraViewModel
import com.alepagani.blogapp.presentation.camera.CameraViewModelFactory

class CameraFragment : Fragment(R.layout.fragment_camera) {

    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var binding: FragmentCameraBinding
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> { CameraViewModelFactory(CameraRepoImpl(CameraDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No se encontro ninguna app para abrir la camara", Toast.LENGTH_SHORT).show()
        }

        initUI()
    }

    private fun initUI() {
        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let {
                viewModel.uploadPhoto(it, binding.editDescription.text.toString()).observe(viewLifecycleOwner, { result ->
                    when(result) {
                        is Result.Loading -> {
                            Toast.makeText(requireContext(), "Uploadin photo..", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgAddPhoto.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}