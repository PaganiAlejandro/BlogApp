package com.alepagani.blogapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.blogapp.core.BaseViewHolder
import com.alepagani.blogapp.data.model.Post
import com.alepagani.blogapp.databinding.PostItemViewBinding
import com.bumptech.glide.Glide
import com.example.blogapp.core.TimeUtils

class HomeScreenAdapter(private val postList: List<Post>): RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount() = postList.size

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ): BaseViewHolder<Post>(binding.root){
        override fun bind(item: Post) {
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postBackground)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name

            val createdAt = (item.created_at?.time?.div(1000))?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }
    }
}