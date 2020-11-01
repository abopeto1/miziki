package com.levagency.miziki.domain.genre.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.genre.entity.Genre
import com.levagency.miziki.domain.genre.entity.asDatabaseModel
import com.levagency.miziki.domain.genre.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GenreRepository(
    val database: MizikiDatabase
) {
    val genres: LiveData<List<Genre>> = Transformations.map(database.genreDao.getAll()){
        it.asDomainModel()
    }

    suspend fun refreshGenre(){
        withContext(Dispatchers.IO){
            when(val result = MizikiApi.genreApiService.getGenres()){
                is Result.Success -> {
                    result.data?.data?.asDatabaseModel()?.let { database.genreDao.insertAll(it) }
                }
                is Result.Failure -> {
                    Timber.i(result.toString())
                    Timber.i(result.code.toString())
                }
                else -> Timber.i(result.toString())
            }
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