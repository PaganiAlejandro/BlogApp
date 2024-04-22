package com.alepagani.blogapp.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    @Exclude @JvmField var id: String = "",   // No quiero enviar esto A firebase, por eso lo escluyo
    @ServerTimestamp val created_at: Date? = null,  //dentro del servidor, al guardarlo como es null, le pone un date de ese momento
    val post_image: String = "",
    val post_description: String = "",
    val poster: Poster? = null,
    val likes: Long = 0,     // aca si recivimos la cantidad de likes del post
    @Exclude @JvmField var liked: Boolean = false   // Este maneja nuestro like. @Exclude NO LO RECIVIMOS DESDE FIREBASE, OSEA NO LO GUARDAMOS EN FIRESTORE, @JVMFIELD, ES PARA QUE SEA UN SINGLETON
)

data class Poster(
    val username: String? = "",
    val uid: String = "",
    val profile_picture: String = ""
)