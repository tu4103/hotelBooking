package com.tu.bookin_hotel.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tu.bookin_hotel.models.BillModel
import com.tu.bookin_hotel.R

interface OnItemClickListenerBill {
    fun onItemClick(position: Int)
}

class HistoryAdapter(private val billList: ArrayList<BillModel>,
                     private val listener: OnItemClickListenerBill) :
    RecyclerView.Adapter<HistoryAdapter.BillViewHolder>() {
    private var onDeleteClickListener: ((BillModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return BillViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        if (billList.isNotEmpty()) {
            val bill = billList[position]
            holder.nameHt.text = bill.hotel.name
            holder.priceHt.text = bill.total
            holder.Days.text = bill.nights
            holder.checkin.text = bill.checkin
            holder.checkout.text = bill.checkout
            holder.quantity.text = bill.quantity
            Glide.with(holder.itemView.context).load(bill.hotel.image)
                .into(holder.image)

            holder.itemView.setOnClickListener {
                listener.onItemClick(position)
            }
            holder.btnUpdate.setOnClickListener {
                listener.onItemClick(position)
            }
            holder.btnDelete.setOnClickListener {
                holder.btnDelete.setOnClickListener {
                    val alertDialog = AlertDialog.Builder(holder.itemView.context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa đơn này?")
                        .setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                            onDeleteClickListener?.invoke(bill)
                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                        }
                        .create()

                    alertDialog.show()
                }
        }


}
    }
    fun setOnDeleteClickListener(listener: (BillModel) -> Unit) {
        onDeleteClickListener = listener
    }
    override fun getItemCount(): Int {
        return billList.size
    }

     class BillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val nameHt= itemView.findViewById<TextView>(R.id.cart_Name_hotel)
         val priceHt= itemView.findViewById<TextView>(R.id.cart_total_price)
         val Days= itemView.findViewById<TextView>(R.id.Days)
         val checkin = itemView.findViewById<TextView>(R.id.cart_checkin)
         val checkout = itemView.findViewById<TextView>(R.id.cart_checkout)
         val quantity = itemView.findViewById<TextView>(R.id.cart_quantity)
         val image = itemView.findViewById<ImageView>(R.id.Hotel_cart_image)
         val btnUpdate = itemView.findViewById<Button>(R.id.Update_bill)
         val btnDelete = itemView.findViewById<Button>(R.id.Delete_bill)
    }

}