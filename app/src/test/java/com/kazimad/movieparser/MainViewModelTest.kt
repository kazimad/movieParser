package com.kazimad.movieparser

import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.entities.SectionedMovieItem
import com.kazimad.movieparser.repo.Repository
import org.junit.Test
import javax.inject.Inject


//@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest() {

    private lateinit var repository: Repository

    @Inject
    constructor(repository: Repository) : this() {
        this.repository = repository
    }

    var moviesLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    @Test
    fun getAllMoviesTest() {
        repository.getAllMovies(errorLiveData, moviesLiveData)
        if (moviesLiveData.value!!.isNotEmpty()) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun showFavorites() {
    }

    @Test
    fun saveFavorites() {
    }

    @Test
    fun showMovieItems() {
    }

    @Test
    fun onMovieButtonClick() {
    }

    @Test
    fun workWithLocalFavoriteData() {
    }
}