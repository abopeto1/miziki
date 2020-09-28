package com.levagency.miziki.network

import com.levagency.miziki.domain.person.entity.DatabasePerson
import com.squareup.moshi.JsonClass

/**
 * Holds a list of persons
 *
 * This is to parse first level of our network result which looks like
 * {
 *  "persons": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkPersonContainer(
    val persons: List<NetworkPerson>
)

/**
 * Person in a app
 */
data class NetworkPerson(
    val id: Long,
    val name: String
)
fun NetworkPersonContainer.asDatabaseModel(): List<DatabasePerson> {
    return persons.map {
        DatabasePerson(
            id = it.id,
            name = it.name
        )
    }
}