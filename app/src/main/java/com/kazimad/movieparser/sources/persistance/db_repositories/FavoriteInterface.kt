package com.kazimad.movieparser.sources.persistance.db_repositories

import com.kazimad.movieparser.entities.FavoriteData

interface FavoriteInterface {
    fun getFavoriteById(id: Int): FavoriteData

    fun insertFavorites(favoriteData: FavoriteData)

    fun deleteAllFavorites()

    fun deleteFavoriteData(id: Int)

    fun getAllFavorites(): List<FavoriteData>

    fun insertAllFavoriteDatas(listFavoriteDatas: List<FavoriteData>)
}