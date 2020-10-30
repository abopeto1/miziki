package com.levagency.miziki.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levagency.miziki.domain.recent_played.entity.DatabaseRecentPlayed
import com.levagency.miziki.domain.recent_played.entity.RecentPlayed

@Dao
interface RecentPlayedDao {
    @Query("SELECT * FROM recent_played")
    fun getRecentPlayed(): LiveData<List<DatabaseRecentPlayed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recentPlayed: List<DatabaseRecentPlayed>)
}