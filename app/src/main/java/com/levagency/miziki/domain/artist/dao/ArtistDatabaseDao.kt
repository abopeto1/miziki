package com.levagency.miziki.domain.artist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levagency.miziki.domain.artist.entity.DatabaseArtist

@Dao
interface ArtistDatabaseDao {
    @Query("SELECT * FROM artist ORDER BY id DESC")
    fun getArtists(): LiveData<List<DatabaseArtist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artists: List<DatabaseArtist>)
}