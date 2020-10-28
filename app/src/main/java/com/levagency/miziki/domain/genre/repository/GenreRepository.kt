package com.levagency.miziki.domain.genre.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.genre.entity.Genre
import com.levagency.miziki.domain.genre.entity.asDatabaseModel
import com.levagency.miziki.domain.genre.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class GenreRepository(
    val database: MizikiDatabase
) {
    val genres: LiveData<List<Genre>> = Transformations.map(database.genreDao.getAll()){
        it.asDomainModel()
    }

    suspend fun refreshGenre(){
        withContext(Dispatchers.IO){
            val networkGenres = MizikiApi.mizikiNetwork.getGenres()

            database.genreDao.insertAll(networkGenres.asDatabaseModel())
        }
    }

//    suspend fun getGenre(id: Long): LiveData<Genre> {
//        withContext(Dispatchers.IO) {
//            val networkGenre = MizikiApi.mizikiNetwork.getGenre(id)
//
//            database.genreDao.insertAll(listOf(networkGenre.asDatabaseModel()))
//        }
//    }
}