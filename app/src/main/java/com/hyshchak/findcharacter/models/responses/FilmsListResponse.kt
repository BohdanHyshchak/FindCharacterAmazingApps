package com.hyshchak.findcharacter.models.responses


import com.google.gson.annotations.SerializedName
import com.hyshchak.findcharacter.database.entities.Film

data class FilmsListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Film>
)