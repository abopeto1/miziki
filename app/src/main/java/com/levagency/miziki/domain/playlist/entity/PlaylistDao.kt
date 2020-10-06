package com.levagency.miziki.domain.playlist.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(playlist: List<DatabasePlaylist>)

    @Query("SELECT * FROM playlist ORDER BY id ASC")
    fun getAll(): LiveData<List<DatabasePlaylist>>
}