package com.tu.bookin_hotel.models

data class BillModel(val hotelname : String,
                     val costumerName: String,
                     val checkin: String,
                     val checkout : String,
                     val nights: Int,
                     val total: String,
                     val Status: String)

