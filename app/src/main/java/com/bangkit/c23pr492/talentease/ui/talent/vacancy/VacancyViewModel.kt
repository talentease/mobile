package com.bangkit.c23pr492.talentease.ui.talent.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VacancyViewModel(private val repository: TalentRepository) : ViewModel() {
    private val _listALlPositionState =
        MutableStateFlow<UiState<List<PositionItemModel>>>(UiState.Initial)
    val listALlPositionState = _listALlPositionState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getAllPosition(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPosition(token).collect { resource ->
                when (resource) {
                    Resource.Loading -> _listALlPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        if (resource.data.data.isNullOrEmpty()) _listALlPositionState.emit(UiState.Empty)
                        else _listALlPositionState.emit(UiState.Success(resource.data.data))

                    }
                    is Resource.Error -> {
                        _listALlPositionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun searchPositions(token: String, newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch(Dispatchers.IO) {
            if (newQuery.isNotBlank() && newQuery.isNotEmpty()) {
                repository.searchPositionsFromName(token, newQuery).collect { resource ->
                    when (resource) {
                        Resource.Loading -> _listALlPositionState.emit(UiState.Loading)
                        is Resource.Success -> {
                            if (resource.data.isNullOrEmpty()) _listALlPositionState.emit(UiState.Empty)
                            else _listALlPositionState.emit(UiState.Success(resource.data))
                        }
                        is Resource.Error -> {
                            _listALlPositionState.emit(UiState.Error(resource.error))
                        }
                    }
                }
            } else getAllPosition(token)
        }
    }
}