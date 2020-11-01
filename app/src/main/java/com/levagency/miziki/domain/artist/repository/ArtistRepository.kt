package com.levagency.miziki.domain.artist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.artist.entity.Artist
import com.levagency.miziki.domain.artist.entity.asDatabaseModel
import com.levagency.miziki.domain.artist.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ArtistRepository(
    private val database: MizikiDatabase
) {
    val artists: LiveData<List<Artist>> = Transformations.map(database.artistDao.getArtists()){
        it.asDomainModel()
    }

    suspend fun refreshArtists(){
        withContext(Dispatchers.IO){
            when(val result = MizikiApi.artistApiService.getArtists()){
                is Result.Success -> {
                    result.data?.artists?.asDatabaseModel()?.let { database.artistDao.insertAll(it) }
                }
                is Result.Failure -> {
                    Timber.i(result.toString())
                    Timber.i(result.code.toString())
                }
                else -> Timber.i(result.toString())
            }
        }
    }
}