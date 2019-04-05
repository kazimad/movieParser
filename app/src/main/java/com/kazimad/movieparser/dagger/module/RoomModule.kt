package com.kazimad.movieparser.dagger.module

import android.content.Context
import androidx.room.Room
import com.kazimad.movieparser.sources.persistance.*
import com.kazimad.movieparser.sources.persistance.daos.FavoriteDao
import com.kazimad.movieparser.sources.persistance.daos.MovieDao
import com.kazimad.movieparser.sources.persistance.data_sources.MovieDbDataSource
import com.kazimad.movieparser.sources.persistance.data_sources.FavoriteDbDataSource
import com.kazimad.movieparser.sources.persistance.db_repositories.FavoriteInterface
import com.kazimad.movieparser.sources.persistance.db_repositories.MovieInterface
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
    internal fun movieRepository(movieDao: MovieDao): MovieInterface {
        return MovieDbDataSource(movieDao)
    }

    @Singleton
    @Provides
    internal fun favoriteRepository(favoriteDao: FavoriteDao): FavoriteInterface {
        return FavoriteDbDataSource(favoriteDao)
    }
}