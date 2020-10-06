package com.levagency.miziki.domain.playlist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.playlist.entity.DatabasePlaylist
import com.levagency.miziki.domain.playlist.entity.NetworkPlaylist
import com.levagency.miziki.domain.playlist.entity.Playlist
import com.levagency.miziki.domain.playlist.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaylistRepository(
    private val database: MizikiDatabase
) {
    val playlists: LiveData<List<Playlist>> = Transformations.map(database.playlistDao.getAll()){
        it.asDomainModel()
    }

    suspend fun refreshPlaylist() {
        withContext(Dispatchers.IO){
            val networkPlaylist = MizikiApi.playlistApiService.getPlaylists()

            database.playlistDao.insertAll(networkPlaylist.asDatabaseModel())
        }
    }
}

private fun List<NetworkPlaylist>.asDatabaseModel(): List<DatabasePlaylist> {
    return map {
        DatabasePlaylist(
            id = it.id,
            numTracks = it.numTracks,
            description = it.description,
            image = it.image,
            name = it.name
        )
    }
}
