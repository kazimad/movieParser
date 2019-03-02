package com.kazimad.movieparser.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kazimad.movieparser.models.response.MovieData

@Database(entities = [MovieData::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val VERSION = "1"
    }

    abstract fun getMovieDao(): MovieDao
}