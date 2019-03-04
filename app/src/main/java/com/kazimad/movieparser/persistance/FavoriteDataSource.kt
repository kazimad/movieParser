package com.kazimad.movieparser.persistance

import com.kazimad.movieparser.models.FavoriteData
import com.kazimad.movieparser.utils.Logger
import javax.inject.Inject

class FavoriteDataSource @Inject
constructor(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    override fun deleteFavoriteData(id: Int) {
        favoriteDao.deleteFavoriteData(id)
    }

    override fun getFavoriteById(id: Int): FavoriteData {
        return favoriteDao.getFavoriteById(id)
    }

    override fun insertFavorites(favoriteData: FavoriteData) {
        Logger.log("FavoriteDataSource insertFavorites ${favoriteData.id}")
        favoriteDao.insertFavorites(favoriteData)
    }

    override fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }

    override fun getAllFavorites(): List<FavoriteData> {
        return favoriteDao.getAllFavorites()
    }


}