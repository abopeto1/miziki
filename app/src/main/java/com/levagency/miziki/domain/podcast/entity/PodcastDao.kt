package com.levagency.miziki.domain.podcast.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PodcastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(podcasts: List<DatabasePodcast>)

    @Query("SELECT * FROM podcast ORDER BY id ASC")
    fun getAll(): LiveData<List<DatabasePodcast>>
}