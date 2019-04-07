package com.kazimad.movieparser.sources.persistance.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kazimad.movieparser.entities.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteEntity WHERE id ==:id")
    fun getFavoriteById(id: Int): FavoriteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity")
    fun deleteAllFavorites()

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    fun deleteFavoriteData(id: Int)

    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavorites(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFavoriteDatas(vararg favoriteEntities: FavoriteEntity)
}