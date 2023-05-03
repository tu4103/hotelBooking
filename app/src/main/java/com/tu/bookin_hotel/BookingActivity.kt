package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.tu.bookin_hotel.fragments.WalletFragment

class BookingActivity : AppCompatActivity() {
    private lateinit var btntype : Button
    private lateinit var btnNext : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        btntype = findViewById(R.id.type_room)
        btnNext = findViewById(R.id.next_step)
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

        btnNext.setOnClickListener() {
            val intent = Intent(this, BookingActivityStep2::class.java)
            startActivity(intent)
        }


    }
}