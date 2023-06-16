package com.bangkit.c23pr492.talentease.ui.talent.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.model.profile.CreateProfileModel
import com.bangkit.c23pr492.talentease.data.model.profile.ProfileModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: TalentRepository) : ViewModel() {
    var readOnly by mutableStateOf(true)
        private set

    fun updateReadOnly(input: Boolean) {
        readOnly = input
    }

    var firstName by mutableStateOf("")
        private set

    fun updateFirstName(input: String) {
        firstName = input
    }

    var lastName by mutableStateOf("")
        private set

    fun updateLastName(input: String) {
        lastName = input
    }

    var email by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        email = input
    }

    var phone by mutableStateOf("")
        private set

    fun updatePhone(input: String) {
        phone = input
    }

    private val _profileState =
        MutableStateFlow<UiState<ProfileModel>>(UiState.Initial)
    val profileState = _profileState.asStateFlow()

    fun getProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTalentId().collect { uid ->
                repository.getProfileById(token, uid ?: "").collect { resource ->
                    when (resource) {
                        Resource.Loading -> {
                            _profileState.emit(UiState.Loading)
                        }
                        is Resource.Error -> {
                            if (resource.error.toString()
                                    .contains("Profile not found", ignoreCase = true)
                            ) {
                                _profileState.emit(UiState.Empty)
                            } else {
                                _profileState.emit(UiState.Error(resource.error))
                            }
                        }
                        is Resource.Success -> {
                            resource.data.data.let {
                                firstName = it.firstName ?: ""
                                lastName = it.lastName ?: ""
                                phone = it.phoneNumber ?: ""
                                email = it.email ?: ""
                            }
                            _profileState.emit(UiState.Success(resource.data))
                        }
                    }
                }
            }
        }
    }

    fun updateProfile(token: String, profile: CreateProfileModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(token, profile).collect { resource ->
                when (resource) {
                    Resource.Loading -> _profileState.emit(UiState.Loading)
                    is Resource.Error -> {
                        _profileState.emit(UiState.Error(resource.error))
                    }
                    is Resource.Success -> {
                        resource.data.data.let {
                            firstName = it.firstName ?: ""
                            lastName = it.lastName ?: ""
                            phone = it.phoneNumber ?: ""
                            email = it.email ?: ""
                        }
                        _profileState.emit(UiState.Success(resource.data))
                    }
                }
            }
        }
    }
}