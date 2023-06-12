package com.bangkit.c23pr492.talentease.ui.talent.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.database.ApplicationWithTalentAndPositionAndCompany
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TalentApplicationViewModel(private val repository: TalentRepository) : ViewModel() {
    private val _listALlApplicationState =
        MutableStateFlow<UiState<List<ApplicationWithTalentAndPositionAndCompany>>>(UiState.Initial)
    val listALlApplicationState = _listALlApplicationState.asStateFlow()

    private val _talentIdState =
        MutableStateFlow<UiState<String>>(UiState.Initial)
    val talentIdState = _talentIdState.asStateFlow()

    init {
        getTalentId()
    }

    fun getTalentId() = viewModelScope.launch(Dispatchers.IO) {
        repository.getTalentId().collect {
            _talentIdState.emit(UiState.Loading)
            if (it.isNullOrBlank()) _talentIdState.emit(UiState.Empty)
            else _talentIdState.emit(UiState.Success(it))
        }
    }

    fun getAllTalentApplication(talentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTalentApplicationWithTalentId(talentId).collect { resource ->
                when (resource) {
                    Resource.Loading -> _listALlApplicationState.emit(UiState.Loading)
                    is Resource.Success -> {
                        resource.data.collect {
                            if (it.isEmpty()) {
                                _listALlApplicationState.emit(UiState.Empty)
                            } else {
                                _listALlApplicationState.emit(
                                    UiState.Success(it)
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _listALlApplicationState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
}