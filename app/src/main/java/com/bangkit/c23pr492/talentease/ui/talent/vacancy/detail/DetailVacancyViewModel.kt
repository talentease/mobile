package com.bangkit.c23pr492.talentease.ui.talent.vacancy.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.database.PositionWithCompany
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailVacancyViewModel(private val repository: TalentRepository) : ViewModel() {
    private val _positionState =
        MutableStateFlow<UiState<PositionWithCompany>>(UiState.Initial)
    val positionState = _positionState.asStateFlow()

    fun getPositionWithCompanyId(positionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPositionWithPositionId(positionId).collect { resource ->
                when (resource) {
                    Resource.Loading -> _positionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        resource.data.collect {
                            _positionState.emit(UiState.Success(it))
                        }
                    }
                    is Resource.Error -> {
                        _positionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
}