package com.tu.bookin_hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.location.LocationRequestCompat.Quality
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tu.bookin_hotel.models.BillModel
import com.tu.bookin_hotel.models.HotelModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class BillDetailActivity : AppCompatActivity() {
    private lateinit var btnplus: Button
    private lateinit var btnminus: Button
    private lateinit var Name: EditText
    private lateinit var Phone: EditText
    private lateinit var Email: EditText
    private lateinit var Checkin: EditText
    private lateinit var Checkout: EditText
    private lateinit var Quantity: TextView
    private lateinit var Type: Button
    private lateinit var btnUpdate : Button
    private lateinit var nights : String
    private  var totals : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_detail)
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val billRef: DatabaseReference = database.getReference("Bill")
        // get data
        val bill = intent.getSerializableExtra("bill") as BillModel
        //set include hotel
        val hotel = bill.hotel
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
        // contact information
        val Id = bill.billId
        val namecustomer = bill.costumerName
        val phone = bill.phone
        val email = bill.email
        val checkin = bill.checkin
        val checkout = bill.checkout
        val quantity = bill.quantity
        val type = bill.type

        Name = findViewById(R.id.up_name)
        Phone = findViewById(R.id.up_phone)
        Email = findViewById(R.id.up_email)
        Checkin = findViewById(R.id.up_checkin)
        Checkout = findViewById(R.id.up_checkout)
        Quantity = findViewById(R.id.up_quantity)
        Type = findViewById(R.id.up_type)

        Name.setText(namecustomer)
        Phone.setText(phone)
        Email.setText(email)
        Checkin.setText(checkin)
        Checkout.setText(checkout)
        Quantity.setText(quantity)
        Type.setText(type)

        btnminus = findViewById(R.id.up_minus)
        btnplus = findViewById(R.id.up_plus)
        btnUpdate = findViewById(R.id.Update)
        btnminus.setOnClickListener {
            if (Quantity.text.toString() > "1") {
                val count = Quantity.text.toString().toInt()
                Quantity.setText((count - 1).toString())
            }
        }
        btnplus.setOnClickListener {
            val count = Quantity.text.toString().toInt()
            Quantity.setText((count + 1).toString())
}
        btnUpdate.setOnClickListener {
            val updatedName = Name.text.toString()
            val updatedPhone = Phone.text.toString()
            val updatedEmail = Email.text.toString()
            val updatedCheckin = Checkin.text.toString()
            val updatedCheckout = Checkout.text.toString()
            val updatedQuantity = Quantity.text.toString()
            val updatedType = Type.text.toString()
            if (updatedCheckin != null && updatedCheckout != null) {
                val count= countdays(updatedCheckin,updatedCheckout)
                 nights = "$count nights"
                 totals =  countprice(count,hotel,100,quantity.toString().toInt())
            }

            val updateData = HashMap<String, Any>()
            updateData["costumerName"] = updatedName
            updateData["phone"] = updatedPhone
            updateData["email"] = updatedEmail
            updateData["checkin"] = updatedCheckin
            updateData["checkout"] = updatedCheckout
            updateData["quantity"] = updatedQuantity
            updateData["type"] = updatedType
            updateData["nights"] = nights
            updateData["total"] = totals

            val billId = bill.billId // Đặt billId tương ứng với ID của hóa đơn
            billRef.child(billId).updateChildren(updateData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    // Thực hiện các hành động khác sau khi cập nhật thành công
                }
                .addOnFailureListener { error ->
                    Toast.makeText(this, "Cập nhật thất bại: ${error.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

private fun countdays(checkin: String, checkout: String) : Int {
    val checkInDate = LocalDate.parse(checkin)
    val checkOutDate = LocalDate.parse(checkout)
    val days = ChronoUnit.DAYS.between(checkInDate, checkOutDate)
    return days.toInt()
}

private fun countprice(days: Int, hotel: HotelModel, discount: Int, quantity:Int): Int {
    val price = hotel.price.toInt()
    val totalPrice = days * price * quantity - discount
    return totalPrice
}
}