package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.tu.bookin_hotel.fragments.WalletFragment
import com.tu.bookin_hotel.models.BillModel
import com.tu.bookin_hotel.models.HotelModel

class BookingActivity : AppCompatActivity() {
    private lateinit var billModel : ArrayList<BillModel>
    private lateinit var customer_name : TextView
    private lateinit var customer_phone : TextView
    private lateinit var customer_email : TextView
    private lateinit var checkin : TextView
    private lateinit var checkout : TextView
    private lateinit var btntype : Button
    private lateinit var btnNext : Button
    private lateinit var btnQuantity : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hotel =intent.getSerializableExtra("hotel") as HotelModel
        setContentView(R.layout.activity_booking)


        customer_name=findViewById(R.id.customer_name)
        customer_phone=findViewById(R.id.customer_phone)
        customer_email=findViewById(R.id.customer_email)
        checkin=findViewById(R.id.check_in)
        checkout=findViewById(R.id.check_out)

        btntype = findViewById(R.id.type_room)
        btnNext = findViewById(R.id.next_step)
        btnQuantity = findViewById(R.id.quantity)
        btntype.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select an option")
            builder.setItems(arrayOf("single", "double")) { dialog, which ->
                // Xử lý tùy chọn được chọn
                when (which) {
                    0 -> {
                        btntype.setText("single")
                    }
                    1 -> {
                        btntype.setText("double")
                    }
                }
            }
            builder.show()
        }
        btnQuantity.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Quantity")
            builder.setItems(arrayOf("1", "2", "3")) { dialog, which ->
                // Xử lý tùy chọn được chọn
                when (which) {
                    0 -> {
                        btntype.setText("1")
                    }
                    1 -> {
                        btntype.setText("2")
                    }
                    2 -> {
                        btntype.setText("3")
                    }
                }
}
        }

        btnNext.setOnClickListener() {
            val name=customer_name.text.toString()
            val phone=customer_phone.text.toString()
            val email=customer_email.text.toString()
            val checkin=checkin.text.toString()
            val checkout=checkout.text.toString()
            val type=btntype.text.toString()
            val quantity=btntype.text.toString()

            val intent = Intent(this, BookingActivityStep2::class.java)
            intent.putExtra("hotel", hotel)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            intent.putExtra("checkin", checkin)
            intent.putExtra("checkout", checkout)
            intent.putExtra("type", type)
            intent.putExtra("quantity", quantity)
            startActivity(intent)
        }


    }
}