package com.tu.bookin_hotel.fragments

import android.content.Intent
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.util.Log
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tu.bookin_hotel.BillDetailActivity
import com.tu.bookin_hotel.HotelDetailActivity
import com.tu.bookin_hotel.R
import com.tu.bookin_hotel.adapters.HistoryAdapter
import com.tu.bookin_hotel.adapters.OnItemClickListenerBill
import com.tu.bookin_hotel.adapters.OnItemClickListenerRecommend
import com.tu.bookin_hotel.models.BillModel
import com.tu.bookin_hotel.models.HotelModel



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), OnItemClickListenerRecommend {
    private lateinit var HistoryAdapter: HistoryAdapter
    private lateinit var Bill_list: ArrayList<BillModel>





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
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val listenerRecommend = object : OnItemClickListenerBill {
            override fun onItemClick(position: Int) {
                val bill = Bill_list[position]
                val intent = Intent(context, BillDetailActivity::class.java)
                intent.putExtra("bill", bill)
                startActivity(intent)
            }
        }


        val HisrecyclerView = view.findViewById<RecyclerView>(R.id.HistoryRecyclerView)
        HisrecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        History_list { list->
            Bill_list = list
            HistoryAdapter = HistoryAdapter(list,listenerRecommend)
            HistoryAdapter.setOnDeleteClickListener { bill->
                deleteBillFromDatabase(bill)
            }
            HisrecyclerView.adapter = HistoryAdapter
        }

        return view
    }

private fun History_list(callback: (ArrayList<BillModel>) -> Unit){
    val db = FirebaseDatabase.getInstance()
    val ref = db.getReference("Bill")
    val list = ArrayList<BillModel>()
    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (childSnapshot in snapshot.children) {
                val BillId= childSnapshot.key
                val hotelSnapshot= childSnapshot.child("hotel")
                val hotel= HotelModel(
                    hotelSnapshot.child("name").value.toString(),
                    hotelSnapshot.child("review").value.toString(),
                    hotelSnapshot.child("rating").value.toString(),
                    hotelSnapshot.child("image").value.toString(),
                    hotelSnapshot.child("location").value.toString(),
                    hotelSnapshot.child("price").value.toString()
                )
                val costumerName = childSnapshot.child("costumerName").value.toString()
                val checkin = childSnapshot.child("checkin").value.toString()
                val checkout = childSnapshot.child("checkout").value.toString()
                val nights = childSnapshot.child("nights").value.toString()
                val quantity = childSnapshot.child("quantity").value.toString()
                val total = childSnapshot.child("total").value.toString()
                val phone = childSnapshot.child("phone").value.toString()
                val email = childSnapshot.child("email").value.toString()
                val type = childSnapshot.child("type").value.toString()
                list.add(BillModel(BillId.toString(),hotel, costumerName, checkin, checkout, nights, quantity, total, phone, email, type))
            }
            callback(list)
            Log.e("list", list.toString())
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })

}
    private fun deleteBillFromDatabase(bill: BillModel) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("Bill").child(bill.billId)
        ref.removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onItemClick(position: Int) {

    }


}