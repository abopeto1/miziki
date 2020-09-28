package com.levagency.miziki.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levagency.miziki.domain.person.entity.DatabasePerson

@Dao
interface PersonDatabaseDao {
    @Query("SELECT * from databasePerson")
    fun getPersons(): LiveData<List<DatabasePerson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(persons: List<DatabasePerson>)
}