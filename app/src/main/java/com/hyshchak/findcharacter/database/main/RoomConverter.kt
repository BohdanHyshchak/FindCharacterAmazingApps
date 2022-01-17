package com.hyshchak.findcharacter.database.main

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {

    val gson = Gson()

    @TypeConverter
    fun arrayListToJson(list: List<String>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun jsonToArrayList(jsonData: String?): List<String>? {
        return if (jsonData == null) null else gson.fromJson(jsonData, object : TypeToken<List<String>?>() {}.type)
    }
}