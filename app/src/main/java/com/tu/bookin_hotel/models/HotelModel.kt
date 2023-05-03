package com.tu.bookin_hotel.models

import java.io.Serializable

data class HotelModel(
    val name: String,
    val review: String,
    val rating: String,
    val image: String,
    val location: String,
    val price: String
): Serializable