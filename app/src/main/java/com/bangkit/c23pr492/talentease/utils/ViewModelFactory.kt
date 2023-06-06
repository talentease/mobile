package com.bangkit.c23pr492.talentease.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.c23pr492.talentease.data.Repository
import com.bangkit.c23pr492.talentease.di.Injection
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.application.ApplicationViewModel

class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> return AuthViewModel(repository) as T
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(repository) as T
//            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> return RegisterViewModel(repository) as T
            modelClass.isAssignableFrom(ApplicationViewModel::class.java) -> return ApplicationViewModel(repository) as T
//            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(repository) as T
//            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return DetailViewModel(repository) as T
//            modelClass.isAssignableFrom(UploadViewModel::class.java) -> return UploadViewModel(repository) as T
//            modelClass.isAssignableFrom(SettingViewModel::class.java) -> return SettingViewModel(repository) as T
//            modelClass.isAssignableFrom(MapsViewModel::class.java) -> return MapsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}