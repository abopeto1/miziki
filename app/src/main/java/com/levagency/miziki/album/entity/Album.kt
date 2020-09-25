package com.levagency.miziki.album.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="album")
data class Album(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String
)