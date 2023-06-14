package com.bangkit.c23pr492.talentease.ui.recruiter.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.application.ApplicationByPositionIdModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ApplicationViewModel(private val repository: RecruiterRepository) : ViewModel() {
    private val _listPositionState =
        MutableStateFlow<UiState<List<PositionItemModel>>>(UiState.Initial)
    val listPositionState = _listPositionState.asStateFlow()

    private val _applicationState =
        MutableStateFlow<UiState<ApplicationByPositionIdModel>>(UiState.Initial)
    val applicationState = _applicationState.asStateFlow()

    private val _listApplication = mutableListOf<ApplicationByPositionIdModel>()
    val listApplication = _listApplication

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getAllPositions(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPositions(token).collect {
                when (it) {
                    Resource.Loading -> _listPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        if (it.data.isNullOrEmpty()) _listPositionState.emit(UiState.Empty)
                        else _listPositionState.emit(UiState.Success(it.data))
                    }
                    is Resource.Error -> _listPositionState.emit(UiState.Error(it.error))
                }
            }
        }
    }

    fun getApplicationByPositionId(token: String, positionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getApplicationByPositionId(token, positionId).collect {
                when(it) {
                    Resource.Loading -> _applicationState.emit(UiState.Loading)
                    is Resource.Error -> _applicationState.emit(UiState.Error(it.error))
                    is Resource.Success -> {
                        _listApplication.add(it.data)
                        _applicationState.emit(UiState.Success(it.data))
                    }
                }
            }
        }
    }

    //    private val _profileState =
//        MutableStateFlow<UiState<ProfileModel>>(UiState.Initial)
//    val profileState = _profileState.asStateFlow()

//    private val _listApplicationWithProfile = MutableStateFlow<UiState<List<ApplicationWithProfile>>>(UiState.Initial)

//    private val _listAllApplicant = mutableListOf<ProfileModel>()
//    val listAllApplicationState = _listAllApplicant
//
//    private val _searchApplicant = mutableListOf<ProfileModel>()
//    val searchApplicationState = _searchApplicant

//    fun getAllApplications(token: String, positionId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getAllApplicationsByPositionId(token, positionId).collect { resource ->
//                when (resource) {
//                    is Resource.Loading -> _listApplicationState.emit(UiState.Loading)
//                    is Resource.Success -> {
//                        resource.data.data.forEach {
//                            getProfileById(token, it.id)
//                        }
//                        _listApplicationState.emit(UiState.Success(resource.data.data))
//                    }
//                    is Resource.Error -> {
//                        _listApplicationState.emit(UiState.Error(resource.error))
//                    }
//                }
//            }
//        }
//    }
//
//    fun getProfileById(token: String, uid: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getProfileById(token, uid).collect {
//                when (it) {
//                    Resource.Loading -> _profileState.emit(UiState.Loading)
//                    is Resource.Success -> {
//                        _listAllApplicant.add(it.data)
//                        _profileState.emit(UiState.Success(it.data))
//                    }
//                    is Resource.Error -> _profileState.emit(UiState.Error(it.error))
//                }
//            }
//        }
//    }
//
//    fun searchApplications(newQuery: String) {
//        _query.value = newQuery
//        viewModelScope.launch(Dispatchers.IO) {
//            _listAllApplicant.filter {
//                it.data.firstName.contains(newQuery, ignoreCase = true)
//            }
//        }
//    }
}