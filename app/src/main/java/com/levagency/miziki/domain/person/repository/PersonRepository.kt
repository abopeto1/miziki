package com.levagency.miziki.domain.person.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.database.database.asDomainModel
import com.levagency.miziki.domain.person.entity.Person
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching miziki persons from the network and storing them on disk
 */
class PersonRepository(
    private val database: MizikiDatabase
){
    val localPersons: LiveData<List<Person>> = Transformations.map(database.personDao.getPersons()){
        it.asDomainModel()
    }
    /**
     * Refresh persons stored in the offline cache
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using "withContext" this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshPersons() {
        withContext(Dispatchers.IO) {
            val persons = MizikiApi.mizikiNetwork.getPersons()
            database.personDao.insertAll(persons.asDatabaseModel())
        }
    }
}