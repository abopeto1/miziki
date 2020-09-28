package com.levagency.miziki.domain.album.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.album.entity.Album
import com.levagency.miziki.domain.album.entity.asDatabaseModel
import com.levagency.miziki.domain.album.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumDataRepository(
    private val database: MizikiDatabase
) {
    val localAlbums: LiveData<List<Album>> = Transformations.map(database.albumDao.getAlbums()){
        it.asDomainModel()
    }

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            val albums = MizikiApi.mizikiNetwork.getAlbums()

            database.albumDao.insertAll(albums.asDatabaseModel())
        }
    }
}