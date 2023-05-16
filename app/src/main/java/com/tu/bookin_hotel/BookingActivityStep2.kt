package com.tu.bookin_hotel

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.tu.bookin_hotel.fragments.WalletFragment
import com.tu.bookin_hotel.models.BillModel
import com.tu.bookin_hotel.models.HotelModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class BookingActivityStep2 : AppCompatActivity() {
    private lateinit var walletFragment: WalletFragment
    private var currentFragmentTag: String? = null
    private lateinit var Checkin :EditText
    private lateinit var Checkout :EditText
    private lateinit var price :TextView
    private lateinit var nights :TextView
    private lateinit var total :TextView
    private lateinit var discount:TextView
    private lateinit var btnComplete : Button
    private lateinit var billModel: BillModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hotel = intent.getSerializableExtra("hotel") as HotelModel
        val customerName = intent.getStringExtra("name")
        val checkin  = intent.getStringExtra("checkin")
        val checkout = intent.getStringExtra("checkout")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val type = intent.getStringExtra("type")
        val quantity = intent.getStringExtra("quantity")
        setContentView(R.layout.activity_booking_step2)


        Checkin=findViewById(R.id.date_checkin)
        Checkout= findViewById(R.id.date_checkout)
        price=findViewById(R.id.Price)
        nights=findViewById(R.id.Nights)
        total=findViewById(R.id.Total)
        discount=findViewById(R.id.Discount)
        btnComplete=findViewById(R.id.btn_complete)


        discount.text="100"
        Checkin.setText("$checkin")
        Checkout.setText("$checkout")
        price.setText(hotel.price +"$")
        val counts = ChronoUnit.DAYS.between(LocalDate.parse(checkin), LocalDate.parse(checkout))
        val totals = counts * hotel.price.toInt()* quantity.toString().toInt() - discount.text.toString().toInt()
        if (checkout != null && checkin != null) {
            val count= countdays(checkin,checkout)
            nights.text = "$count nights"
           val totals =  countprice(count,hotel,100,quantity.toString().toInt())
            total.setText(totals.toString()+"$")
        }


        btnComplete.setOnClickListener(){
            val db= FirebaseDatabase.getInstance()
            val myRef=db.getReference("Bill")
            val billId = myRef.push().key
            if (quantity != null) {
                billModel=BillModel(billId.toString(),hotel,customerName.toString(),checkin.toString(),checkout.toString(),counts.toString(),quantity,totals.toString(),phone.toString(),email.toString(),type.toString())
            }

            myRef.push().setValue(billModel).addOnSuccessListener(){
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                finish()
                val i= Intent(this,HomeActivity::class.java)
                startActivity(i)
            }
        }



        walletFragment = WalletFragment()
        replaceFragment(walletFragment, "WalletFragment")

        val walletNavigation = findViewById<BottomNavigationView>(R.id.walet_navigation)
        walletNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.walet -> replaceFragment(walletFragment, "WalletFragment")
                R.id.MoMo -> replaceFragment(walletFragment, "WalletFragment")
            }
            true
}
    }






    private fun replaceFragment(fragment: Fragment, tag: String) {
        currentFragmentTag = tag
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_paymen, fragment, tag)
            .commit()
    }
    private fun countdays(checkin: String, checkout: String) : Int {
        val checkInDate = LocalDate.parse(checkin)
        val checkOutDate = LocalDate.parse(checkout)
        val days = ChronoUnit.DAYS.between(checkInDate, checkOutDate)
        return days.toInt()
    }

    private fun countprice(days: Int, hotel: HotelModel, discount: Int ,quantity:Int): Int {
        val price = hotel.price.toInt()
        val totalPrice = days * price * quantity - discount
        return totalPrice
    }
}