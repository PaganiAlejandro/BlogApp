package com.alepagani.blogapp.data.model

import java.sql.Timestamp

data class Post(
    val profile_picture: String = "",
    val profile_name: String = "",
    val post_timestamp: Timestamp? = null,
    val post_image: String = ""
)