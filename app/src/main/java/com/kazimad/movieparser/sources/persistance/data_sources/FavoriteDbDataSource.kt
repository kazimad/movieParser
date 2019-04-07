package com.kazimad.movieparser.sources.persistance.data_sources

import com.kazimad.movieparser.entities.FavoriteEntity
import com.kazimad.movieparser.sources.persistance.db_repositories.FavoriteInterface
import com.kazimad.movieparser.sources.persistance.daos.FavoriteDao
import javax.inject.Inject

class FavoriteDbDataSource @Inject
constructor(private val favoriteDao: FavoriteDao) :
    FavoriteInterface {

    override fun insertAllFavoriteDatas(listFavoriteEntities: List<FavoriteEntity>) {
        favoriteDao.insertAllFavoriteDatas(*listFavoriteEntities.toTypedArray())
    }

    override fun deleteFavoriteData(id: Int) {
        favoriteDao.deleteFavoriteData(id)
    }

    override fun getFavoriteById(id: Int): FavoriteEntity {
        return favoriteDao.getFavoriteById(id)
    }

    override fun insertFavorites(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorites(favoriteEntity)
    }

    override fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }

    override fun getAllFavorites(): List<FavoriteEntity> {
        return favoriteDao.getAllFavorites()
    }


}