package com.hyshchak.findcharacter.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hyshchak.findcharacter.data.MainRepository
import com.hyshchak.findcharacter.database.entities.PersonShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class FavoriteListViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _favoriteList = repository.getFavoriteListFromDb()
    val favoriteList = _favoriteList.asLiveData(Dispatchers.IO)

    fun updatePerson(person: PersonShort) {
        viewModelScope.launch {
            repository.savePersonShort(PersonShort(person.name, !person.isFavorite, person.id))
        }
    }
}
