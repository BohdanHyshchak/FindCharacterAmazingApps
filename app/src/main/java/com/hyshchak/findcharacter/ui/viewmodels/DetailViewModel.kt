package com.hyshchak.findcharacter.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hyshchak.findcharacter.data.MainRepository
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class DetailViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val personShort = MutableStateFlow<PersonShort?>(null)

    fun getPersonFromFragment(person: PersonShort) {
        personShort.tryEmit(person)
        viewModelScope.launch {
            repository.savePersonShort(person)
        }
    }

    private val _personFullInfo = personShort.flatMapLatest {
        val query = "${Constants.BASE_URL}people/${it!!.id}/"
        repository.getFullPersonFromDb(query)
    }

    val personFullInfo = _personFullInfo.asLiveData(Dispatchers.IO)

    private val _isLiked = personShort.flatMapLatest {
        MutableStateFlow(it!!.isFavorite)
    }

    val isLiked = _isLiked.asLiveData(Dispatchers.IO)
}
