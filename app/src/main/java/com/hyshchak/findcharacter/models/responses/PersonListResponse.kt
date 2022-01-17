package com.hyshchak.findcharacter.models.responses


import com.google.gson.annotations.SerializedName
import com.hyshchak.findcharacter.database.entities.Person

data class PersonListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Person>
)