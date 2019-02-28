package com.kazimad.movieparser.dagger.module

import android.app.Application
import androidx.room.Room
import com.kazimad.movieparser.persistance.AppDatabase
import com.kazimad.movieparser.persistance.DbDataSource
import com.kazimad.movieparser.persistance.DbRepository
import com.kazimad.movieparser.persistance.MovieDao
import com.kazimad.movieparser.utils.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {

    private lateinit var db: AppDatabase

    init {
        db = Room.databaseBuilder(mApplication, AppDatabase::class.java, "demo-db").build()
        Logger.log("RoomModule init ${db.VERSION}")
    }

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): AppDatabase {
        return db
    }

    @Singleton
    @Provides
    internal fun providesProductDao(dataBase: AppDatabase): MovieDao {
        return dataBase.getProductDao()
    }

    @Singleton
    @Provides
    internal fun productRepository(productDao: MovieDao): DbRepository {
        return DbDataSource(productDao)
    }

}