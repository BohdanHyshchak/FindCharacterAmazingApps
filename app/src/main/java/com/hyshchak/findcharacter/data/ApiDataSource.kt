package com.hyshchak.findcharacter.data

import com.hyshchak.findcharacter.models.responses.FilmsListResponse
import com.hyshchak.findcharacter.models.responses.PersonListResponse
import com.hyshchak.findcharacter.network.ApiService
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val apiService: ApiService
) {
    // Todo: handle exceptions

    suspend fun getPeopleList(page: Int?): Response<PersonListResponse>? {
        val response = apiService.getPeopleList(page)
        return if (response.isSuccessful) {
            response
        } else {
            null
        }
    }

    suspend fun getFilmsList(): Response<FilmsListResponse>? {
        val response = apiService.getFilmList()
        return if (response.isSuccessful) {
            response
        } else {
            null
        }
    }
}
