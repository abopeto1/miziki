package com.levagency.miziki.domain.person.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DatabasePerson represents a person room entity in the local storage database
 */
@Entity
data class DatabasePerson constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
)