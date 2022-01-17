package com.hyshchak.findcharacter.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hyshchak.findcharacter.data.MainRepository
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class DetailViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val personShort = MutableStateFlow<PersonShort?>(null)

    init {
        viewModelScope.launch {
            repository.getAndSaveAllFilms()
        }
    }

    fun getPersonFromFragment(person: PersonShort) {
        personShort.tryEmit(person)
        viewModelScope.launch {
            repository.savePersonShort(person)
        }
    }

    private val _personFullInfo = personShort.flatMapLatest {
        val query = "${Constants.BASE_URL}people/${it!!.id}/"
        val fullInfo = repository.getFullPersonFromDb(query)
        fullInfo
    }

    val personFullInfo = _personFullInfo.asLiveData(Dispatchers.IO)

    private val _filmsList = _personFullInfo.flatMapLatest {
        val person = it
        val urlList = person.films
        flow { emit(repository.getFilmsByUrl(urlList)) }
    }

    val filmsList = _filmsList.asLiveData(Dispatchers.IO)

    private val _isLiked = personShort.flatMapLatest {
        MutableStateFlow(it?.isFavorite ?: false)
    }

    val isLiked = _isLiked.asLiveData(Dispatchers.IO)
}
