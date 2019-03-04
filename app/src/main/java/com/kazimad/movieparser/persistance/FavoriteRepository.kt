package com.kazimad.movieparser.persistance

import com.kazimad.movieparser.models.FavoriteData

interface FavoriteRepository {
    fun getFavoriteById(id: Int): FavoriteData

    fun insertFavorites(favoriteData: FavoriteData)

    fun deleteAllFavorites()

    fun deleteFavoriteData(id: Int)

    fun getAllFavorites(): List<FavoriteData>
}