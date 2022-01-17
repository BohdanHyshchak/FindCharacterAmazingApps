package com.hyshchak.findcharacter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "people_short")
data class PersonShort(
    val name: String,
    val isFavorite: Boolean,
    @PrimaryKey() val id: Int
) : Serializable
