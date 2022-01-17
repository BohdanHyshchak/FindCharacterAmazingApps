package com.hyshchak.findcharacter.database.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hyshchak.findcharacter.database.dao.PersonDao
import com.hyshchak.findcharacter.database.entities.Film
import com.hyshchak.findcharacter.database.entities.Person
import com.hyshchak.findcharacter.database.entities.PersonShort

@Database(
    entities = [Person::class, PersonShort::class, Film::class],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class PeopleDatabase : RoomDatabase() {
    abstract val personDao: PersonDao
}