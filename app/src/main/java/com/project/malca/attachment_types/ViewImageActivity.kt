package com.project.malca.attachment_types

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.project.malca.IMAGE
import com.project.malca.NAME
import com.project.malca.R
import com.project.malca.databinding.ActivityViewImageBinding
import com.squareup.picasso.Picasso

class ViewImageActivity : AppCompatActivity() {
    private val name by lazy {
        intent.getStringExtra(NAME)
    }
    private val image by lazy {
        intent.getStringExtra(IMAGE)
    }
    private val msg by lazy {
        Uri.parse(intent.getStringExtra("MSG"))
    }
    lateinit var binding: ActivityViewImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nameTv.text=name
        if(name=="YOU"){
            binding.userImgView.isVisible=false
        }
        else{
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.defaultavatar)
                .error(R.drawable.defaultavatar)
                .into(binding.userImgView)
        }
        Picasso.get()
            .load(msg)
            .placeholder(R.drawable.defaultavatar)
            .error(R.drawable.defaultavatar)
            .into(binding.sentImage)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}