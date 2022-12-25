package com.project.malca.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.project.malca.MainActivity
import com.project.malca.R
import com.project.malca.databinding.ActivityMoreDetailsBinding
import com.project.malca.models.User

class MoreDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreDetailsBinding
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    val database by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sentPhoto = intent.getStringExtra("ImageURI")
        val prefs = getSharedPreferences("FileName", MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("userDetails", "")

        val user: User =
            gson.fromJson(json, User::class.java) as User

        binding.radioGroup.setOnCheckedChangeListener { _, checkedIn ->
            binding.submitBtn.isEnabled = true
            when (checkedIn) {
                R.id.alumniRB -> {
                    binding.alumni.visibility = View.VISIBLE
                    binding.student.visibility = View.GONE
                }
                R.id.studentRB -> {
                    binding.alumni.visibility = View.GONE
                    binding.student.visibility = View.VISIBLE
                }

            }
        }
        binding.submitBtn.setOnClickListener {

            if (binding.radioGroup.checkedRadioButtonId == R.id.alumniRB) {
                if (!binding.companyEdTv.text.isNullOrEmpty() && !binding.rollNoEdTv.text.isNullOrEmpty() && !binding.countryEdTv.text.isNullOrEmpty()) {
                    user.company = binding.companyEdTv.text.toString()
                    user.rollNo = binding.rollNoEdTv.text.toString()
                    user.country = binding.countryEdTv.text.toString()
                    user.alumni = true
                    database.collection("users").document(auth.uid!!).set(user)
                        .addOnSuccessListener {

                            startActivity(Intent(this, MainActivity::class.java))

                        }.addOnFailureListener {
                        }
                } else {
                    Toast.makeText(this, "Complete all fields", Toast.LENGTH_LONG).show()
                }
            } else {
                if (!binding.userImgView.text.isNullOrEmpty()) {
                    user.company = null
                    user.rollNo = binding.userImgView.text.toString()
                    user.country = null
                    user.alumni = false
                    database.collection("users").document(auth.uid!!).set(user)
                        .addOnSuccessListener {

                            startActivity(Intent(this, MainActivity::class.java))

                        }.addOnFailureListener {
                        }
                } else {
                    Toast.makeText(this, "Complete all fields", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}