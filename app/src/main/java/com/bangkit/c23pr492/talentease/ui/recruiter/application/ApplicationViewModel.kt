package com.bangkit.c23pr492.talentease.ui.recruiter.application

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.application.DataItem
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApplicationViewModel(private val repository: RecruiterRepository) : ViewModel() {
    private val _listApplicationState =
        MutableStateFlow<UiState<MutableList<DataItem>>>(UiState.Initial)
    val listApplicationState = _listApplicationState.asStateFlow()

    var dataItem by mutableStateOf(mutableListOf<DataItem>())
        private set

    private fun addItem(data: DataItem) {
        dataItem.add(data)
    }

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getAllPositions(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPositions(token).collect {
                when (it) {
                    Resource.Loading -> _listApplicationState.emit(UiState.Loading)
                    is Resource.Success -> {
                        Log.d("kenapa", "getAllPositions: sekali dua kali tiga enam")
                        it.data?.forEach { position ->
                            repository.getApplicationByPositionId(token, position.id)
                                .collect { application ->
                                    when (application) {
                                        Resource.Loading -> _listApplicationState.emit(UiState.Loading)
                                        is Resource.Error -> _listApplicationState.emit(
                                            UiState.Error(
                                                application.error
                                            )
                                        )
                                        is Resource.Success -> {
                                            application.data.data.forEach { item ->
                                                addItem(item)
                                                Log.d(
                                                    "kenapa",
                                                    "getApplicationByPositionId: ${item.id}"
                                                )
                                            }
                                        }
                                    }
                                }
                        }
                        _listApplicationState.emit(UiState.Success(dataItem))
                    }
                    is Resource.Error -> _listApplicationState.emit(UiState.Error(it.error))
                }
            }
        }
    }

//    private fun getApplicationByPositionId(token: String, positionId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//
//        }
//    }

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