package com.kazimad.movieparser.dagger.module

import android.app.Application
import androidx.room.Room
import com.kazimad.movieparser.persistance.AppDatabase
import com.kazimad.movieparser.persistance.MovieDao
import com.kazimad.movieparser.persistance.ProductDataSource
import com.kazimad.movieparser.persistance.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {

    private val db: AppDatabase

    init {
        db = Room.databaseBuilder(mApplication, AppDatabase::class.java, "demo-db").build()
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
    internal fun productRepository(productDao: MovieDao): ProductRepository {
        return ProductDataSource(productDao)
    }

}