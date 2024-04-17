package com.alepagani.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.alepagani.blogapp.R
import com.alepagani.blogapp.core.Resource
import com.alepagani.blogapp.data.model.Post
import com.alepagani.blogapp.data.remote.HomeScreenDataSource
import com.alepagani.blogapp.databinding.FragmentHomeScreenBinding
import com.alepagani.blogapp.domain.HomeScreeRepoImpl
import com.alepagani.blogapp.presentation.HomeScreenViewModel
import com.alepagani.blogapp.presentation.HomeScreenViewModelFactory
import com.alepagani.blogapp.ui.home.adapter.HomeScreenAdapter
import com.google.firebase.Timestamp

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> { HomeScreenViewModelFactory(HomeScreeRepoImpl(HomeScreenDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
