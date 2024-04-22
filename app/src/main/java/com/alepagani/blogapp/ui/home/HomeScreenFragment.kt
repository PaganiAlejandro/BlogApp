package com.alepagani.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alepagani.blogapp.R
import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.core.hide
import com.alepagani.blogapp.core.show
import com.alepagani.blogapp.data.model.Post
import com.alepagani.blogapp.data.remote.home.HomeScreenDataSource
import com.alepagani.blogapp.databinding.FragmentHomeScreenBinding
import com.alepagani.blogapp.domain.Home.HomeScreeRepoImpl
import com.alepagani.blogapp.presentation.home.HomeScreenViewModel
import com.alepagani.blogapp.presentation.home.HomeScreenViewModelFactory
import com.alepagani.blogapp.ui.home.adapter.HomeScreenAdapter
import com.alepagani.blogapp.ui.home.adapter.OnPostClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), OnPostClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> { HomeScreenViewModelFactory(HomeScreeRepoImpl(HomeScreenDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.latestPost.collect { result ->
                    when (result) {
                        is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            if (result.data.isEmpty()) {
                                binding.emptyContariner.show()
                                return@collect
                            } else {
                                binding.emptyContariner.hide()
                            }
                            binding.rvHome.adapter = HomeScreenAdapter(result.data, this@HomeScreenFragment)
                        }
                        is Result.Failure -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Ocurrio un error ${result.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }


    //Manera anterior y similar de pedir los datos al viewmodel, por medio de Livedata

        // viewModel.fetchLatestPosts().observe(viewLifecycleOwner, { result ->
        //     when (result) {
        //         is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
        //         is Result.Success -> {
        //             binding.progressBar.visibility = View.GONE
        //             if (result.data.isEmpty()) {
        //                 binding.emptyContariner.show()
        //                 return@observe
        //             } else {
        //                 binding.emptyContariner.hide()
        //             }
        //             binding.rvHome.adapter = HomeScreenAdapter(result.data, this)
        //         }
        //         is Result.Failure -> {
        //             binding.progressBar.visibility = View.GONE
        //             Toast.makeText(
        //                 requireContext(),
        //                 "Ocurrio un error ${result.exception}",
        //                 Toast.LENGTH_SHORT
        //             ).show()
        //         }
        //     }
        // })
    }

    override fun onLikeButtonClick(post: Post, liked: Boolean) {
        viewModel.registerLikeButtonState(post.id, liked).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {}
                is Result.Failure -> {
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
