package com.kazimad.movieparser

import com.kazimad.movieparser.dagger.component.DaggerTestComponent
import com.kazimad.movieparser.dagger.component.TestComponent
import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModuleTest
import com.kazimad.movieparser.entities.FavoriteEntity
import com.kazimad.movieparser.entities.MovieEntity
import com.kazimad.movieparser.entities.SectionedMovieItem
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.utils.Constants
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.*


class RepositoryTest {


    lateinit var testComponent: TestComponent
    lateinit var app: App

    private var favoritesList: MutableList<FavoriteEntity> = mutableListOf()
    private var favoriteIdsList: MutableList<Int> = mutableListOf()
    private var allMoviesList: MutableList<MovieEntity> = mutableListOf()
    private lateinit var movieEntity1: MovieEntity
    private lateinit var movieEntity2: MovieEntity
    private lateinit var movieEntity3: MovieEntity

    @Before
    fun setUp() {
        app = mock(App::class.java)

        testComponent = DaggerTestComponent.builder()
            .appModule(AppModule())
            .roomModuleTest(RoomModuleTest())
            .contextModule(ContextModule(app))
            .build()

        movieEntity1 = DummyData.createDummyMovieEntity()
        movieEntity2 = DummyData.createDummyMovieEntity()
        movieEntity3 = DummyData.createDummyMovieEntity()

        favoritesList.clear()
        favoriteIdsList.clear()
        allMoviesList.clear()
    }

    @Test
    fun getFavoriteIdsValues() {
        val favoriteEntityToAdd = FavoriteEntity(DummyData.movieId)
        favoriteIdsList.add(favoriteEntityToAdd.id)
        Assert.assertTrue(favoriteEntityToAdd.id == favoriteIdsList[favoriteIdsList.size - 1])
    }

    @Test
    fun setFavoriteIdsValues() {
        val favoriteEntityToAdd = FavoriteEntity(DummyData.movieId)
        val sizeBefore = favoriteIdsList.size
        favoriteIdsList.add(favoriteEntityToAdd.id)
        val sizeAfter = favoriteIdsList.size
        Assert.assertTrue(sizeBefore < sizeAfter)
    }

    @Test
    fun pickFavoritesFromMovieDataPositive() {
        val result: MutableList<MovieEntity> = mutableListOf()

        allMoviesList.add(movieEntity1)
        allMoviesList.add(movieEntity2)
        allMoviesList.add(movieEntity3)

        favoriteIdsList.add(movieEntity1.id)
        favoriteIdsList.add(movieEntity2.id)
        favoriteIdsList.add(movieEntity3.id)

        allMoviesList.forEach {
            if (favoriteIdsList.contains(it.id)) {
                result.add(it)
            }
        }
        Assert.assertTrue(result.size == 3)
        Assert.assertEquals(allMoviesList[0].id, result[0].id)
    }

    @Test
    fun pickFavoritesFromMovieDataNegative() {
        val result: MutableList<MovieEntity> = mutableListOf()

        allMoviesList.add(movieEntity1)
        allMoviesList.add(movieEntity2)
        allMoviesList.add(movieEntity3)

        favoriteIdsList.add(5555)
        favoriteIdsList.add(6666)
        favoriteIdsList.add(66666)

        allMoviesList.forEach {
            if (favoriteIdsList.contains(it.id)) {
                result.add(it)
            }
        }
        Assert.assertTrue(result.size != 3)
        Assert.assertTrue(result.size == 0)
    }

    @Test
    fun markFavorite() {
        val sectionItemsList = mutableListOf<SectionedMovieItem>()

        val sectionMovieItem1 = DummyData.createDummySectionedMovieItem()
        val sectionMovieItem2 = DummyData.createDummySectionedMovieItem()
        val sectionMovieItem3 = DummyData.createDummySectionedMovieItem()

        favoriteIdsList.add(movieEntity1.id)
        favoriteIdsList.add(movieEntity2.id)
        favoriteIdsList.add(movieEntity3.id)

        sectionItemsList.add(sectionMovieItem1)
        sectionItemsList.add(sectionMovieItem2)
        sectionItemsList.add(sectionMovieItem3)

        sectionItemsList.forEach {
            it.value?.isFavorite = favoriteIdsList.contains(it.value?.id)
        }

    }

    @Test
    fun sortAndFilterValues() {
        val fullFormat = Constants.fullDateFormat
        val shortFormat = Constants.shortFormat
        var currentMonth: Int = -1
        val unpreparedList: MutableList<MovieEntity> = mutableListOf()
        unpreparedList.add(
            MovieEntity(
                DummyData.movieId++,
                DummyData.adult,
                DummyData.backdropPath,
                DummyData.generIds,
                DummyData.originalLanguage,
                DummyData.originalTitle,
                DummyData.overview,
                DummyData.popularity,
                DummyData.posterPath,
                DummyData.releaseDate3,
                DummyData.title,
                DummyData.video,
                DummyData.voteAverage,
                DummyData.voteCount,
                DummyData.isFavorite
            )
        )
        unpreparedList.add(
            MovieEntity(
                DummyData.movieId++,
                DummyData.adult,
                DummyData.backdropPath,
                DummyData.generIds,
                DummyData.originalLanguage,
                DummyData.originalTitle,
                DummyData.overview,
                DummyData.popularity,
                DummyData.posterPath,
                DummyData.releaseDate2,
                DummyData.title,
                DummyData.video,
                DummyData.voteAverage,
                DummyData.voteCount,
                DummyData.isFavorite
            )
        )
        unpreparedList.add(
            MovieEntity(
                DummyData.movieId++,
                DummyData.adult,
                DummyData.backdropPath,
                DummyData.generIds,
                DummyData.originalLanguage,
                DummyData.originalTitle,
                DummyData.overview,
                DummyData.popularity,
                DummyData.posterPath,
                DummyData.releaseDate,
                DummyData.title,
                DummyData.video,
                DummyData.voteAverage,
                DummyData.voteCount,
                DummyData.isFavorite
            )
        )


        val technical: MutableList<SectionedMovieItem> = mutableListOf()
        val sortedListByMonth = unpreparedList.sortedWith(compareBy { it.releaseDate })


        // separate by month
        sortedListByMonth.forEach {
            run {
                val releaseDate = it.releaseDate
                val dateFull = fullFormat.parse(releaseDate)
                val dateShort = shortFormat.format(dateFull)
                val cal = Calendar.getInstance()!!
                cal.time = dateFull
                val month = cal.get(Calendar.MONTH)

                if (month != currentMonth) {
                    technical.add(SectionedMovieItem(ListTypes.HEADER, null, dateShort))
                    technical.add(SectionedMovieItem(ListTypes.REGULAR, it, null))
                    currentMonth = month

                    Assert.assertTrue(technical[technical.size - 1].type == ListTypes.REGULAR)
                    Assert.assertTrue(technical[technical.size - 2].type == ListTypes.HEADER)
                } else {
                    technical.add(SectionedMovieItem(ListTypes.REGULAR, it, null))
                }
            }
        }

        //separate values in each collection by dates
        val rawHashMap = HashMap<String?, ArrayList<SectionedMovieItem>?>()
        var array: ArrayList<SectionedMovieItem>? = null
        var lastKey: String? = null

        technical.forEach {
            run {
                if (it.type == ListTypes.HEADER) {
                    if (lastKey != null) {
                        rawHashMap[lastKey] = array
                    }
                    lastKey = it.headerText
                    array = ArrayList()
                } else {
                    array?.add(it)
                }
            }
        }
        rawHashMap[lastKey] = array

        //sort each value list by popularity
        val sortedHashMap = HashMap<String?, List<SectionedMovieItem>?>()
        rawHashMap.forEach { (key, value) ->
            run {
                val sortedList = value?.sortedWith(compareBy { it.value?.popularity })
                sortedHashMap[key] = sortedList
            }
        }

        rawHashMap.forEach { (key, value) ->
            run {

                Assert.assertTrue(value != null)
                for (i in 0 until value!!.size) {
                    if (i != value.size - 1) {
                        val next = value[i + 1].value!!.popularity
                        val curr = value[i].value!!.popularity

                        Assert.assertNotNull(next)
                        Assert.assertNotNull(curr)
                        Assert.assertTrue(curr!! <= next!!)
                    }
                }
            }
        }
        // compose sortered values and headers
        val sortedFilteredResult = ArrayList<SectionedMovieItem>()
        sortedHashMap.forEach { (key, value) ->
            run {
                if (value != null) {
                    sortedFilteredResult.add(
                        SectionedMovieItem(
                            ListTypes.HEADER,
                            null,
                            key
                        )
                    )
                    sortedFilteredResult.addAll(ArrayList(value))
                }
            }
        }

        Assert.assertTrue(sortedFilteredResult.isNotEmpty())

        var prevMonth = 0
        for (i in 0 until sortedFilteredResult.size - 1) {
            if (sortedFilteredResult[i].type == ListTypes.REGULAR) {
                val dateFull = fullFormat.parse(sortedFilteredResult[i].value!!.releaseDate)
                val cal = Calendar.getInstance()!!
                cal.time = dateFull
                val month = cal.get(Calendar.MONTH)
                Assert.assertTrue(month > prevMonth)
                prevMonth = month
            } else {
                val dateShort = shortFormat.parse(sortedFilteredResult[i].headerText)
                val cal = Calendar.getInstance()!!
                cal.time = dateShort
                val month = cal.get(Calendar.MONTH)

                Assert.assertNotNull(sortedFilteredResult[i + 1].value)
                val dateNext = fullFormat.parse(sortedFilteredResult[i + 1].value!!.releaseDate)
                val calNext = Calendar.getInstance()!!
                calNext.time = dateNext
                val monthNext = cal.get(Calendar.MONTH)

                Assert.assertTrue(month == monthNext)
            }
        }
    }

    @Test
    fun saveFavorites() {
        favoriteIdsList.add(movieEntity1.id)
        favoriteIdsList.add(movieEntity2.id)
        favoriteIdsList.add(movieEntity3.id)

        val async = GlobalScope.async {
            withContext(Dispatchers.IO) {
                testComponent.getRepository().favoriteDataSource.deleteAllFavorites()
                favoritesList.clear()

                Assert.assertTrue(favoritesList.size == 0)

                favoriteIdsList.forEach { favoritesList.add(FavoriteEntity(it)) }

                Assert.assertTrue(favoritesList.size == favoriteIdsList.size)
                Assert.assertEquals(favoritesList[0], favoriteIdsList[0])

                testComponent.getRepository().favoriteDataSource.insertAllFavoriteDatas(favoritesList)
                val listAllFavoritesTest =
                    testComponent.getRepository().favoriteDataSource.getAllFavorites()

                Assert.assertTrue(listAllFavoritesTest.isNotEmpty())

                for (i in 0..listAllFavoritesTest.size) {
                    Assert.assertEquals(listAllFavoritesTest[i].id, favoritesList[i].id)
                }
            }
        }
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                async.await()
            }
        }
    }

    @Test
    fun getAllMovies() {

    }
}