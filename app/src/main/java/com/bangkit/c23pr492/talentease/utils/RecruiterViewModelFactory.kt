package com.bangkit.c23pr492.talentease.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.di.Injection
import com.bangkit.c23pr492.talentease.ui.recruiter.application.ApplicationViewModel
import com.bangkit.c23pr492.talentease.ui.recruiter.position.PositionViewModel

class RecruiterViewModelFactory private constructor(private val repository: RecruiterRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ApplicationViewModel::class.java) -> return ApplicationViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(PositionViewModel::class.java) -> return PositionViewModel(
                repository
            ) as T
//            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return DetailViewModel(repository) as T
//            modelClass.isAssignableFrom(UploadViewModel::class.java) -> return UploadViewModel(repository) as T
//            modelClass.isAssignableFrom(SettingViewModel::class.java) -> return SettingViewModel(repository) as T
//            modelClass.isAssignableFrom(MapsViewModel::class.java) -> return MapsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RecruiterViewModelFactory? = null
        fun getInstance(context: Context): RecruiterViewModelFactory = instance ?: synchronized(this) {
            instance ?: RecruiterViewModelFactory(Injection.provideRecruiterRepository(context))
        }.also { instance = it }
    }
}