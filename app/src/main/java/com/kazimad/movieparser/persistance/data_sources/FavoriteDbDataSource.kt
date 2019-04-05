package com.kazimad.movieparser.persistance.data_sources

import com.kazimad.movieparser.entities.FavoriteData
import com.kazimad.movieparser.persistance.db_repositories.FavoriteInterface
import com.kazimad.movieparser.persistance.daos.FavoriteDao
import javax.inject.Inject

class FavoriteDbDataSource @Inject
constructor(private val favoriteDao: FavoriteDao) :
    FavoriteInterface {

    override fun insertAllFavoriteDatas(listFavoriteDatas: List<FavoriteData>) {
        favoriteDao.insertAllFavoriteDatas(*listFavoriteDatas.toTypedArray())
    }

    override fun deleteFavoriteData(id: Int) {
        favoriteDao.deleteFavoriteData(id)
    }

    override fun getFavoriteById(id: Int): FavoriteData {
        return favoriteDao.getFavoriteById(id)
    }

    override fun insertFavorites(favoriteData: FavoriteData) {
        favoriteDao.insertFavorites(favoriteData)
    }

    override fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }

    override fun getAllFavorites(): List<FavoriteData> {
        return favoriteDao.getAllFavorites()
    }


}