package com.hyshchak.findcharacter.network

import com.hyshchak.findcharacter.models.responses.FilmsListResponse
import com.hyshchak.findcharacter.models.responses.PersonListResponse
import com.hyshchak.findcharacter.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("people")
    suspend fun getPeopleList(
        @Query("page")
        page: Int? = Constants.PAGING_FIRST_PAGE_NUMBER,
    ): Response<PersonListResponse>

    @GET("films")
    suspend fun getFilmList(): Response<FilmsListResponse>
}
