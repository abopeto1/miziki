package com.levagency.miziki.domain.album.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levagency.miziki.domain.album.entity.DatabaseAlbum

@Dao
interface AlbumDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<DatabaseAlbum>)

    @Query("SELECT * FROM album order by id DESC")
    fun getAlbums(): LiveData<List<DatabaseAlbum>>

    @Query("SELECT * FROM album ORDER BY id DESC LIMIT 1")
    fun getAlbum(): LiveData<DatabaseAlbum?>
}