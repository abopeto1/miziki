package com.levagency.miziki.album.repository

import androidx.lifecycle.LiveData
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import com.levagency.miziki.album.entity.Album

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