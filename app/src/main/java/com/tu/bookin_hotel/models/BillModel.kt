package com.tu.bookin_hotel.models

import java.io.Serializable

data class BillModel(val billId: String,
                     val hotel: HotelModel,
                     val costumerName: String,
                     val checkin: String,
                     val checkout : String,
                     val nights: String,
                     val quantity: String,
                     val total: String,
                     val phone : String,
                     val email : String,
                     val type : String
                     ) : Serializable

