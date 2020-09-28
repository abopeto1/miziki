package com.levagency.miziki.database.database

import com.levagency.miziki.domain.person.entity.DatabasePerson
import com.levagency.miziki.domain.person.entity.Person

fun List<DatabasePerson>.asDomainModel() : List<Person> {
    return map {
        Person(
            id = it.id,
            name = it.name
        )
    }
}