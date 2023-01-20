package com.example.masir.db.converter

import androidx.room.TypeConverter
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User
import com.google.gson.Gson

class SingleUserConverters {

    @TypeConverter
    fun fromUserToJson(user: User): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromJsonToUser(restaurantString: String): SingleUserObj {
        return Gson().fromJson(restaurantString , SingleUserObj::class.java)
    }


}