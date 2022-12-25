package com.project.malca

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.malca.databinding.ActivityUserDetailsBinding
import com.project.malca.models.User
import com.squareup.picasso.Picasso
import java.util.Calendar

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
        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }
        database.collection("users").whereEqualTo("name", name).get().addOnSuccessListener {
            for (i in it) {
                val user = i.toObject(User::class.java)
                Picasso.get()
                    .load(user.imageUrl)
                    .placeholder(R.drawable.defaultavatar)
                    .error(R.drawable.defaultavatar)
                    .into(binding.userImgView)
                binding.apply {
                    if (user.alumni) {
                        locationTv.text = "Location: ${user.country}"
                        companyTv.text = "Company: ${user.country}"
                        val yearop = user.rollNo.subSequence(9, 11).toString().toInt() + 4
                        yopTv.text = "Year passed out: 20$yearop"
                    } else {
                        alumni.visibility = View.GONE
                        locationTv.visibility = View.GONE
                        companyTv.visibility = View.GONE
                        val yop = user.rollNo.subSequence(9, 11).toString().toInt()
                        yopTv.text = "Year in: ${formatYear(calculateYear(yop))}"
                    }
                    nameTv.text = "Name: ${user.name}"
                    ratingTv.text = user.rating.toString()
                    val size = user.skills.size
                    if (size >= 1) {
                        binding.skillLayout2.isVisible = true
                        binding.skillTv1.text = user.skills[0]
                    }
                    if (size >= 2) {
                        binding.skillLayout2.isVisible = true
                        binding.skillTv2.text = user.skills[1]
                    }
                    if (size == 3) {
                        binding.skillLayout3.isVisible = true
                        binding.skillTv3.text = user.skills[2]
                    }
                }
            }
        }
    }

    fun formatYear(year: Int): String {
        return when (year) {
            1 -> {
                "First"
            }
            2 -> {
                "Second"
            }
            3 -> {
                "Third"
            }
            4 -> {
                "Fourth"
            }
            else -> {
                "NA"
            }
        }
    }

    private fun calculateYear(yop: Int): Int {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val actualPassingYear = 2000 + yop
        val year = currentYear - actualPassingYear
        return if (currentMonth >= 7) {
            year + 1
        } else {
            year
        }
    }
}