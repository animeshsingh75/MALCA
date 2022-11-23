package com.project.malca.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.project.malca.R
import com.project.malca.databinding.ActivityInitialReviewProfileBinding
import com.squareup.picasso.Picasso

class InitialReviewProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityInitialReviewProfileBinding
    private val sentphoto by lazy {
        Uri.parse(intent.getStringExtra("SENTPHOTO"))
    }

    private val source: String? by lazy {
        intent.getStringExtra("source")
    }
    val storage by lazy {
        FirebaseStorage.getInstance()
    }
    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialReviewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        Picasso.get()
            .load(sentphoto)
            .placeholder(R.drawable.defaultavatar)
            .error(R.drawable.defaultavatar)
            .into(binding.sentImage)
        binding.btnSend.setOnClickListener {
            if (source == null) {
                val intent = Intent(this, SignUpActivity::class.java)
                intent.putExtra("ImageURI", sentphoto.toString())
                startActivity(intent)
            } else {
                val intent = Intent(this, MoreDetailsActivity::class.java)
                intent.putExtra("ImageURI", sentphoto.toString())
                startActivity(intent)
            }
        }
    }
}
