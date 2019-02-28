package com.kazimad.movieparser.persistance

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataConverter {

    @TypeConverter
    fun fromGanresList(countryLang: List<Int>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toGanresList(countryLangString: String?): List<Int>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(countryLangString, type)
    }
}