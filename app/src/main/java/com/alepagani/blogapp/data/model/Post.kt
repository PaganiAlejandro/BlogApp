package com.alepagani.blogapp.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    val profile_picture: String = "",
    val profile_name: String = "",
    @ServerTimestamp val created_at: Date? = null,  //dentro del servidor, al guardarlo como es null, le pone un date de ese momento
    val post_image: String = "",
    val post_description: String = "",
    val uid: String = ""
)