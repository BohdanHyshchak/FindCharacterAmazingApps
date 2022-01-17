package com.hyshchak.findcharacter.data

import androidx.paging.*
import com.hyshchak.findcharacter.database.entities.Film
import com.hyshchak.findcharacter.database.entities.Person
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.database.main.PeopleDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val peopleDatabase: PeopleDatabase,
) {

    private val personDao = peopleDatabase.personDao
    @ExperimentalPagingApi
    fun getPeopleListPaging(query: String?): Flow<PagingData<PersonShort>> =
        Pager(
            PagingConfig(pageSize = 10),
            remoteMediator = PeopleRemoteMediator(apiDataSource = apiDataSource, peopleDatabase = peopleDatabase),
            pagingSourceFactory = {
                personDao.getAllPeopleShort()
            }
        ).flow.map { data ->
            data.filter { personShort -> personShort.name.contains(query ?: "", ignoreCase = true) }
        }

    fun getFullPersonFromDb(url: String) = flow<Person> { emit(personDao.getPersonByQuery(url)) }

    suspend fun savePersonShort(person: PersonShort) = personDao.savePeopleShortList(listOf(person))

    fun getFavoriteListFromDb() = personDao.getFavoritePeopleShortList()

    suspend fun getAndSaveAllFilms() {
        if (personDao.filmsCount() == 0) {
            val filmList = apiDataSource.getFilmsList()
            if (filmList != null && filmList.isSuccessful) {
                personDao.saveFilmsList(filmList.body()!!.results)
            } else
                personDao.saveFilmsList(emptyList())
        }
    }

    suspend fun getFilmsByUrl(urlList: List<String>): List<String> {
        val mutableList = mutableListOf<String>()
        if (personDao.filmsCount() != 0)
            for (i in urlList) {
                mutableList.add(personDao.getFilmByUrl(i).title)
            }
        return mutableList
    }
}
