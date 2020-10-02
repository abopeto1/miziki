package com.levagency.miziki.domain.genre.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenreDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<DatabaseGenre>)

    @Query("SELECT * FROM genre ORDER BY id DESC")
    fun getAll(): LiveData<List<DatabaseGenre>>
}