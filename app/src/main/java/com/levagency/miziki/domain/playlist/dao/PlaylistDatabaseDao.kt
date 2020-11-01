package com.levagency.miziki.domain.playlist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levagency.miziki.domain.playlist.entity.DatabasePlaylist

@Dao
interface PlaylistDatabaseDao {
    @Query("SELECT * FROM playlist ORDER BY id DESC")
    fun getPlaylists(): LiveData<List<DatabasePlaylist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(playlists: List<DatabasePlaylist>)
}