package com.levagency.miziki.domain.playlist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.playlist.entity.*
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PlaylistRepository(
    private val database: MizikiDatabase
) {
    val playlists: LiveData<List<Playlist>> = Transformations.map(database.playlistDao.getPlaylists()){
        it.asDomainModel()
    }

    suspend fun refreshPlaylist() {
        withContext(Dispatchers.IO){
            when (val result = MizikiApi.playlistApiService.getPlaylists()) {
                is Result.Success -> {
                    result.data?.data?.asDatabaseModel()?.let { database.playlistDao.insertAll(it) }
                }
                is Result.Failure -> {
                    Timber.i(result.toString())
                }
                else -> Timber.i(result.toString())
            }
        }
    }
}

