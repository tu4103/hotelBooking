package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.tu.bookin_hotel.adapters.HotelAdapter
import com.tu.bookin_hotel.adapters.OnItemClickListenerHotel
import com.tu.bookin_hotel.models.HotelModel

class SearchActivity : AppCompatActivity() {
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var hotels: ArrayList<HotelModel>
    private lateinit var searchView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

         searchView = findViewById(R.id.search_recycler_view)
        searchView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val listenerhotel = object : OnItemClickListenerHotel {
            override fun onItemClick(position: Int) {
                val hotel: HotelModel = hotels[position]
                val intent = Intent(this@SearchActivity, HotelDetailActivity::class.java)
                intent.putExtra("hotel", hotel)
                startActivity(intent)
}
        }
        hotelList { list ->
            hotels = list
            hotelAdapter = HotelAdapter(list, listenerhotel)
            searchView.adapter = hotelAdapter
        }
    }

    private fun hotelList(callback: (ArrayList<HotelModel>) -> Unit) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("hotel")
        val list = ArrayList<HotelModel>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val name = childSnapshot.child("name").value.toString()
                    val review = childSnapshot.child("review").value.toString()
                    val rating = childSnapshot.child("rating").value.toString()
                    val image = childSnapshot.child("image").value.toString()
                    val location = childSnapshot.child("location").value.toString()
                    val price = childSnapshot.child("price").value.toString()
                    val hotel = HotelModel(name, review, rating, image, location, price)
                    list.add(hotel)
                }
                callback(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}