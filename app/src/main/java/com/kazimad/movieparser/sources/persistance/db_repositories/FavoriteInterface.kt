package com.kazimad.movieparser.sources.persistance.db_repositories

import com.kazimad.movieparser.entities.FavoriteEntity

interface FavoriteInterface {
    fun getFavoriteById(id: Int): FavoriteEntity

    fun insertFavorites(favoriteEntity: FavoriteEntity)

    fun deleteAllFavorites()

    fun deleteFavoriteData(id: Int)

    fun getAllFavorites(): List<FavoriteEntity>

    fun insertAllFavoriteDatas(listFavoriteEntities: List<FavoriteEntity>)
}