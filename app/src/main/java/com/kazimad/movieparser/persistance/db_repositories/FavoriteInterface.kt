package com.kazimad.movieparser.persistance.db_repositories

import com.kazimad.movieparser.models.FavoriteData

interface FavoriteInterface {
    fun getFavoriteById(id: Int): FavoriteData

    fun insertFavorites(favoriteData: FavoriteData)

    fun deleteAllFavorites()

    fun deleteFavoriteData(id: Int)

    fun getAllFavorites(): List<FavoriteData>

    fun insertAllFavoriteDatas(listFavoriteDatas: List<FavoriteData>)
}