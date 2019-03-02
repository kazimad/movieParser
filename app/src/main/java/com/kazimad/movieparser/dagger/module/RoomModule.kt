package com.kazimad.movieparser.dagger.module

import android.content.Context
import androidx.room.Room
import com.kazimad.movieparser.persistance.AppDatabase
import com.kazimad.movieparser.persistance.DbDataSource
import com.kazimad.movieparser.persistance.DbRepository
import com.kazimad.movieparser.persistance.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {


//    @Singleton
//    @Provides
//    fun provideTvMazeDatabase(application: Application): AppDatabase {
//        return Room.databaseBuilder(
//            application,
//            AppDatabase::class.java, AppDatabase.VERSION
//        )
//            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideShowDao(tvMazeDatabase: AppDatabase): MovieDao {
//        return tvMazeDatabase.getMovieDao()
//    }


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
    internal fun movieRepository(movieDao: MovieDao): DbRepository {
        return DbDataSource(movieDao)
    }

}