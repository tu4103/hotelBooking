package com.tu.bookin_hotel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tu.bookin_hotel.R
import com.tu.bookin_hotel.models.HotelModel

interface OnItemClickListenerRecommend {
    fun onItemClick(position: Int)
}

class HotelAdapter2(
    private var hotel_list: ArrayList<HotelModel>,
    private val listener: OnItemClickListenerRecommend
) :
    RecyclerView.Adapter<HotelAdapter2.HotelViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelAdapter2.HotelViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.hotel_item_2, parent, false)
        return HotelAdapter2.HotelViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return hotel_list.size
    }

    override fun onBindViewHolder(holder: HotelAdapter2.HotelViewHolder, position: Int) {
        val currentHotel = hotel_list[position]
        holder.nameHotel.text = currentHotel.name
        holder.reviewHotel.text = currentHotel.review
        holder.ratingHotel.rating = currentHotel.rating.toFloat()
        Glide.with(holder.itemView.context).load(currentHotel.image)
            .into(
                holder.imageHotel
            )
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    class HotelViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val nameHotel: TextView = itemView.findViewById(R.id.name_hotel_2)
        val reviewHotel: TextView = itemView.findViewById(R.id.review_hotel_2)
        val ratingHotel: RatingBar = itemView.findViewById(R.id.rating_hotel_2)
        val imageHotel: ImageView = itemView.findViewById(R.id.image_hotel_2)
    }

}

