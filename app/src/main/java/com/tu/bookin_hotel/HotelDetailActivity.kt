package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tu.bookin_hotel.models.HotelModel

class HotelDetailActivity : AppCompatActivity() {
    private lateinit var btnBook : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hotel = intent.getSerializableExtra("hotel") as HotelModel
        setContentView(R.layout.activity_hotel_detail)
        val nameHotel: TextView = findViewById(R.id.name_hotel)
        val reviewHotel: TextView = findViewById(R.id.review_hotel)
        val ratingHotel: RatingBar = findViewById(R.id.rating_hotel)
        val imageHotel: ImageView = findViewById(R.id.image_hotel)
        val locationHotel: TextView = findViewById(R.id.location_hotel)
        val priceHotel: TextView = findViewById(R.id.price_hotel)
        nameHotel.text = hotel.name
        reviewHotel.text = hotel.review
        ratingHotel.rating = hotel.rating.toFloat()
        locationHotel.text = hotel.location
        priceHotel.text = hotel.price
        Glide.with(this).load(hotel.image).into(imageHotel)


        btnBook = findViewById(R.id.Booking)
        btnBook.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
}
    }
}
