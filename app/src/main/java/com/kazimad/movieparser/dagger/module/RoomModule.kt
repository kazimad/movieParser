package com.kazimad.movieparser.dagger.module

import android.content.Context
import androidx.room.Room
import com.kazimad.movieparser.persistance.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Singleton
    @Provides
    internal fun providesRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "demo-db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    internal fun providesMovieDao(dataBase: AppDatabase): MovieDao {
        return dataBase.getMovieDao()
    }

    @Singleton
    @Provides
    internal fun providesFavoriteDao(dataBase: AppDatabase): FavoriteDao {
        return dataBase.getFavoriteDao()
    }

    @Singleton
    @Provides
    internal fun movieRepository(movieDao: MovieDao): DbRepository {
        return DbDataSource(movieDao)
    }

    @Singleton
    @Provides
    internal fun favoriteRepository(favoriteDao: FavoriteDao): FavoriteRepository {
        return FavoriteDataSource(favoriteDao)
    }
}