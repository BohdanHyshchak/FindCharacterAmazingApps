package com.hyshchak.findcharacter.data

import android.net.Uri
import android.util.Log
import androidx.paging.*
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.database.main.PeopleDatabase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.ceil

@ExperimentalPagingApi
class PeopleRemoteMediator @Inject constructor(
    private val apiDataSource: ApiDataSource,
    peopleDatabase: PeopleDatabase,
) : RemoteMediator<Int, PersonShort>() {

    private val personDao = peopleDatabase.personDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PersonShort>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH, LoadType.APPEND -> {
                    Log.d("HELP", "itemsCount = ${personDao.itemsCount()}personDao.itemsCount() / 10 + 1 = ${personDao.itemsCount() / 10 + 1}")
                    ceil(personDao.itemsCount() / 10.0).toInt() + 1
                }
                LoadType.PREPEND -> {
                    Log.d("HELP", "PREPEND")
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
            }

            val response = apiDataSource.getPeopleList(loadKey)
            val peopleList = if (response != null) { response.body()!!.results } else { emptyList() }
            val personShortList = peopleList.map { PersonShort(it.name, false, Uri.parse(it.url).lastPathSegment!!.toInt()) }

            personDao.savePeopleShortList(personShortList)
            personDao.savePeopleList(peopleList)

            MediatorResult.Success(
                endOfPaginationReached = if (response != null) {
                    response.body()!!.next == null
                } else true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
