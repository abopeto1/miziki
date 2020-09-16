package com.levagency.miziki.repositories

import androidx.lifecycle.LiveData
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import com.levagency.miziki.models.Album

class AlbumDataRepository(
    private val albumDatabaseDao: AlbumDatabaseDao,
) {
    fun getAlbum(albumId: Long): LiveData<Album?> {
        return albumDatabaseDao.getAlbum()
    }

    fun getAllAlbum(): LiveData<List<Album>> {
        return albumDatabaseDao.getAllAlbums()
    }
}