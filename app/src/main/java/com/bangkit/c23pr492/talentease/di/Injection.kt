package com.bangkit.c23pr492.talentease.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.c23pr492.talentease.data.Repository
import com.bangkit.c23pr492.talentease.data.datastore.AuthDataStore
import com.google.firebase.auth.FirebaseAuth

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")
object Injection {
    fun provideRepository(context: Context): Repository {
//        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(
            FirebaseAuth.getInstance(),
            AuthDataStore.getInstance(context.dataStore)
        )
    }
}