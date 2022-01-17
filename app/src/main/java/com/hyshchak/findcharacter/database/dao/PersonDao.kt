package com.hyshchak.findcharacter.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hyshchak.findcharacter.database.entities.Film
import com.hyshchak.findcharacter.database.entities.Person
import com.hyshchak.findcharacter.database.entities.PersonShort
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    /**
     * Person DAO
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePeopleList(peopleList: List<Person>)

    @Query("SELECT * FROM people WHERE url = :query")
    fun getPersonByQuery(query: String): Person

    /**
     * Person_short DAO
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePeopleShortList(peopleShortList: List<PersonShort>)

    @Query("SELECT * FROM people_short")
    fun getAllPeopleShort(): PagingSource<Int, PersonShort>

    @Query("SELECT * FROM people_short WHERE isFavorite = 1")
    fun getFavoritePeopleShortList(): Flow<List<PersonShort>>

    @Query("SELECT COUNT(id) FROM people_short")
    fun itemsCount(): Int

    /**
     * Film DAO
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmsList(filmsList: List<Film>)

    @Query("SELECT * FROM films WHERE url =:url")
    fun getFilmByUrl(url: String): Film

    @Query("SELECT COUNT(url) FROM films")
    fun filmsCount(): Int
}
