package com.hyshchak.findcharacter.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyshchak.findcharacter.data.MainRepository
import com.hyshchak.findcharacter.database.entities.PersonShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")

    fun setQuery(q: String?) {
        if (!q.isNullOrBlank() && q != query.value) {
            Log.d("ViewModel", "q = $q and query = ${query.value}")
            query.tryEmit(q)
        }
    }

    private val _peopleList = query.flatMapLatest { query ->
        repository.getPeopleListPaging(query).cachedIn(viewModelScope)
    }

    val peopleList: Flow<PagingData<PersonShort>> = _peopleList

    fun updatePerson(person: PersonShort) {
        viewModelScope.launch {
            repository.savePersonShort(PersonShort(person.name, !person.isFavorite, person.id))
        }
    }
}
