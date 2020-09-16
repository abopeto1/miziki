package com.levagency.miziki.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.levagency.miziki.models.Album

@Dao
interface AlbumDatabaseDao {
    @Insert
    suspend fun insert(album: Album)

    @Update
    suspend fun update(album: Album)

    @Query("DELETE FROM album")
    suspend fun clear()

    @Query("SELECT * FROM album order by id DESC")
    fun getAllAlbums(): LiveData<List<Album>>

    @Query("SELECT * FROM album ORDER BY id DESC LIMIT 1")
    fun getAlbum(): LiveData<Album?>
}