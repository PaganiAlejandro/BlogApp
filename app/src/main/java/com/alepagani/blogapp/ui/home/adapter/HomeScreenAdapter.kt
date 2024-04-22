package com.alepagani.blogapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.blogapp.R
import com.alepagani.blogapp.core.BaseViewHolder
import com.alepagani.blogapp.core.hide
import com.alepagani.blogapp.core.show
import com.alepagani.blogapp.data.model.Post
import com.alepagani.blogapp.databinding.PostItemViewBinding
import com.bumptech.glide.Glide
import com.example.blogapp.core.TimeUtils

class HomeScreenAdapter(private val postList: List<Post>, private val onPostClickListener: OnPostClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postClickListener: OnPostClickListener? = null

    init {
        postClickListener = onPostClickListener
    }

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
            loadPostImage(item)
            setupProfileInfo(item)
            setupTimeStamps(item)
            tintHeratIcon(item)
            setupLikeCount(item)
            setClicLkdAction(item)
        }


        private fun loadPostImage(item: Post) {
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postBackground)
        }

        private fun setupTimeStamps(post: Post) {
            val createdAt = (post.created_at?.time?.div(1000))?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }

        private fun setupProfileInfo(post: Post) {
            Glide.with(context).load(post.poster?.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = post.poster?.username
        }

        private fun tintHeratIcon(item: Post) {
            if (!item.liked) {
                binding.likeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_24))
                binding.likeButton.setColorFilter(ContextCompat.getColor(context, R.color.black))
            } else {
                binding.likeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_24))
                binding.likeButton.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }
        }

        private fun setupLikeCount(item: Post) {
            if (item.likes > 0) {
                binding.likeCount.show()
                binding.likeCount.text = "${item.likes} likes"
            } else {
                binding.likeCount.hide()
            }
        }

        private fun setClicLkdAction(item: Post) {
            binding.likeButton.setOnClickListener {
                if (item.liked) item.apply { liked = false } else item.apply { liked = true }
                tintHeratIcon(item)
                postClickListener?.onLikeButtonClick(item, item.liked)
            }
        }
    }
}

interface OnPostClickListener {
    fun onLikeButtonClick(post: Post, liked: Boolean)
}