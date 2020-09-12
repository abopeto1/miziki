package com.levagency.sanjola.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.levagency.sanjola.models.Album

@Dao
interface AlbumDatabaseDao {
    @Insert
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Query("DELETE FROM album")
    fun clear()

    @Query("SELECT * FROM album order by id DESC")
    fun getAllAlbums(): LiveData<List<Album>>
}