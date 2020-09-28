package com.levagency.miziki.domain.person.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.person.repository.PersonRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException

class PersonViewModel(
    application: Application
): AndroidViewModel(application){
    private val personRepository = PersonRepository(getDatabase(application))
    val persons = personRepository.localPersons

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() =
        viewModelScope.launch {
            try {
                personRepository.refreshPersons()
            } catch (e: IOException){
//                if(persons.value.isNullOrEmpty()){
//
//                }
            }
        }
}

@Suppress("UNCHECKED_CAST")
class Factory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PersonViewModel::class.java)){
            return PersonViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

}