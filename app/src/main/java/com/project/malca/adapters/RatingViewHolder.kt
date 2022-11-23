package com.project.malca.adapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.malca.R
import com.project.malca.models.FriendRating
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date

class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: FriendRating) =
        with(itemView) {
            val nameTv = findViewById<TextView>(R.id.nameTv)
            val ratingTv = findViewById<TextView>(R.id.ratingTv)
            val dateTv = findViewById<TextView>(R.id.dateTv)
            val userImgView = findViewById<ImageView>(R.id.userImgView)
            nameTv.text = item.name
            Log.d("RatingUser", getDateTime(item.sentAt)!!)
            Log.d("RatingUser", item.imageUrl)
            ratingTv.text = item.indiRating.toFloat().toString()
            Picasso.get()
                .load(item.imageUrl)
                .placeholder(R.drawable.defaultavatar)
                .error(R.drawable.defaultavatar)
                .into(userImgView)
            dateTv.text = getDateTime(item.sentAt)
        }

    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}