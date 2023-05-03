package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.tu.bookin_hotel.models.HotelModel

class uploadActivity : AppCompatActivity() {
    private lateinit var Name : EditText
    private lateinit var Price : EditText
    private lateinit var Review : EditText
    private lateinit var Location : EditText
    private lateinit var Rating : EditText
    private lateinit var Image : EditText
    private lateinit var SaveBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        Name = findViewById(R.id.upload_name)
        Price = findViewById(R.id.upload_price)
        Review = findViewById(R.id.upload_review)
        Location = findViewById(R.id.upload_location)
        Rating = findViewById(R.id.upload_rating)
        Image = findViewById(R.id.upload_image)
        SaveBtn = findViewById(R.id.save)

        SaveBtn.setOnClickListener {
           Save()
        }

    }
    private fun Save(){
    val name = Name.text.toString()
    val price = Price.text.toString()
    val review = Review.text.toString()
    val location = Location.text.toString()
    val rating = Rating.text.toString()
    val image = Image.text.toString()
      val db = FirebaseDatabase.getInstance()
     val hotel = HotelModel(name,review,rating,image,location,price)
        db.getReference("hotel").push().setValue(hotel).addOnCompleteListener {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            Name.text.clear()
            Price.text.clear()
            Review.text.clear()
            Location.text.clear()
            Rating.text.clear()
            Image.text.clear()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
}
}


