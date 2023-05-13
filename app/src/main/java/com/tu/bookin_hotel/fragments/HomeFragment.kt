package com.tu.bookin_hotel.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tu.bookin_hotel.HotelDetailActivity
import com.tu.bookin_hotel.R
import com.tu.bookin_hotel.SearchActivity
import com.tu.bookin_hotel.adapters.HotelAdapter
import com.tu.bookin_hotel.models.HotelModel
import com.tu.bookin_hotel.adapters.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var hotels: ArrayList<HotelModel>
    private lateinit var hotelAdapter2: HotelAdapter2
    private lateinit var hotelRecommended: ArrayList<HotelModel>
    private lateinit var btnsearch : Button

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //bestchoice recycler view
        val bestChoiceRecyclerView = view.findViewById<RecyclerView>(R.id.bestChoice_recyclerView)
        bestChoiceRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val listenerRecommend = object : OnItemClickListenerRecommend {
            override fun onItemClick(position: Int) {
                val hotel: HotelModel = hotels[position]
                val intent = Intent(context, HotelDetailActivity::class.java)
                intent.putExtra("hotel", hotel)
                startActivity(intent)
            }
        }
        val listenerHotel = object : OnItemClickListenerHotel {
            override fun onItemClick(position: Int) {
                val hotel: HotelModel = hotels[position]
                val intent = Intent(context, HotelDetailActivity::class.java)
                intent.putExtra("hotel", hotel)
                startActivity(intent)
            }
        }
        hotelList { list ->
            hotels = list
            hotelAdapter = HotelAdapter(list, listenerHotel)
            bestChoiceRecyclerView.adapter = hotelAdapter
        }
        val recommendedRecyclerView = view.findViewById<RecyclerView>(R.id.recommend_recyclerView)
        recommendedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hotelList { list ->
            hotelRecommended = list
            hotelAdapter2 = HotelAdapter2(list, listenerRecommend)
            recommendedRecyclerView.adapter = hotelAdapter2
        }

        btnsearch = view.findViewById(R.id.btn_hotel_search)
        btnsearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        return view
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
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}