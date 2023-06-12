package com.bangkit.c23pr492.talentease.ui.talent.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.database.PositionWithCompany
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VacancyViewModel(private val repository: TalentRepository): ViewModel() {
    private val _listALlPositionState =
        MutableStateFlow<UiState<List<PositionWithCompany>>>(UiState.Initial)
    val listALlPositionState = _listALlPositionState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    
    fun getAllPosition() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPosition().collect { resource ->
                when (resource) {
                    Resource.Loading -> _listALlPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        resource.data.collect {
                            if (it.isEmpty()) {
                                _listALlPositionState.emit(UiState.Empty)
                            } else {
                                _listALlPositionState.emit(
                                    UiState.Success(it)
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _listALlPositionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun searchPositions(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch(Dispatchers.IO) {
            if (newQuery.isNotBlank() && newQuery.isNotEmpty()) {
                repository.searchPositionFromName(newQuery).collect { resource ->
                    when (resource) {
                        Resource.Loading -> _listALlPositionState.emit(UiState.Loading)
                        is Resource.Success -> {
                            resource.data.collectLatest {
                                if (it.isNotEmpty()) _listALlPositionState.emit(UiState.Success(it))
                                else _listALlPositionState.emit(UiState.Empty)
                            }
                        }
                        is Resource.Error -> {
                            _listALlPositionState.emit(UiState.Error(resource.error))
                        }
                    }
                }
            } else getAllPosition()
        }
    }
}