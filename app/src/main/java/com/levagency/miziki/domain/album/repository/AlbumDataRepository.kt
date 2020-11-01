package com.levagency.miziki.domain.album.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.album.entity.Album
import com.levagency.miziki.domain.album.entity.asDatabaseModel
import com.levagency.miziki.domain.album.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AlbumDataRepository(
    private val database: MizikiDatabase
) {
    val albums: LiveData<List<Album>> = Transformations.map(database.albumDao.getAlbums()){
        it.asDomainModel()
    }

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            when(val result = MizikiApi.albumApiService.getAlbums()){
                is Result.Success -> {
                    result.data?.albums?.asDatabaseModel()?.let { database.albumDao.insertAll(it) }
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