package com.alepagani.blogapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


    }
}

// val db = FirebaseFirestore.getInstance()
// db.collection("ciudades").document("LA").get().addOnSuccessListener {document ->
//     document?.let {
//         Log.d("Firebase", "Datos: ${it.data}")
//     }
// }.addOnFailureListener { error ->
//     Log.e("Firebase error", "Error: ${error?.message}")
// }