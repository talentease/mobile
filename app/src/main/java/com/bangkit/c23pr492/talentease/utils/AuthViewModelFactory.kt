package com.bangkit.c23pr492.talentease.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.c23pr492.talentease.data.AuthRepository
import com.bangkit.c23pr492.talentease.di.Injection
import com.bangkit.c23pr492.talentease.ui.AuthViewModel

class AuthViewModelFactory private constructor(private val repository: AuthRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: AuthViewModelFactory? = null
        fun getInstance(context: Context): AuthViewModelFactory = instance?: synchronized(this) {
            instance ?: AuthViewModelFactory(Injection.provideAuthRepository(context))
        }.also { instance = it }
    }
}