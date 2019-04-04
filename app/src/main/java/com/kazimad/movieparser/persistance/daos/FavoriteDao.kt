package com.kazimad.movieparser.persistance.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kazimad.movieparser.models.FavoriteData
import com.kazimad.movieparser.models.MovieData

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteData WHERE id ==:id")
    fun getFavoriteById(id: Int): FavoriteData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favoriteData: FavoriteData)

    @Query("DELETE FROM FavoriteData")
    fun deleteAllFavorites()

    @Query("DELETE FROM FavoriteData WHERE id = :id")
    fun deleteFavoriteData(id: Int)

    @Query("SELECT * FROM FavoriteData")
    fun getAllFavorites(): List<FavoriteData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFavoriteDatas(vararg favoriteDatas: FavoriteData)
}