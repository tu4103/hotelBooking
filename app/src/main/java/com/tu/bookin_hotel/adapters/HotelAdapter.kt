package com.tu.bookin_hotel.adapters

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tu.bookin_hotel.R
import com.tu.bookin_hotel.models.HotelModel
import java.io.Serializable

interface OnItemClickListenerHotel {
    fun onItemClick(position: Int)
}

class HotelAdapter(
    private var hotel_list: ArrayList<HotelModel>,
    private val listener: OnItemClickListenerHotel
) :
    RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelAdapter.HotelViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelAdapter.HotelViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return hotel_list.size
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        if(hotel_list.isNotEmpty()){
            val currentHotel = hotel_list[position]
            holder.nameHotel.text = currentHotel.name
            holder.reviewHotel.text = currentHotel.review
            holder.ratingHotel.rating = currentHotel.rating.toFloat()
            holder.locationHotel.text = currentHotel.location
            holder.priceHotel.text = currentHotel.price
            Glide.with(holder.itemView.context).load(currentHotel.image)
                .into(holder.imageHotel)
            holder.itemView.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    class HotelViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val nameHotel: TextView = itemView.findViewById(R.id.name_hotel)
        val reviewHotel: TextView = itemView.findViewById(R.id.review_hotel)
        val ratingHotel: RatingBar = itemView.findViewById(R.id.rating_hotel)
        val imageHotel: ImageView = itemView.findViewById(R.id.image_hotel)
        val locationHotel: TextView = itemView.findViewById(R.id.location_hotel)
        val priceHotel: TextView = itemView.findViewById(R.id.price_hotel)
    }
    fun filter(list: ArrayList<HotelModel>) {
         hotel_list = list
        notifyDataSetChanged()
    }
}


