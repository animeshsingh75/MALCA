package com.project.malca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.malca.databinding.ActivityUserDetailsBinding

class UserDetailsActivity : AppCompatActivity() {
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val name by lazy {
        intent.getStringExtra(NAME)
    }
    val database by lazy {
        FirebaseFirestore.getInstance()
    }
    lateinit var binding: ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database.collection("users").whereEqualTo("name", name).get().addOnSuccessListener {
            for (i in it) {
                binding.text.text = i.data.toString()
            }
        }
    }
}