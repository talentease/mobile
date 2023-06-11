package com.bangkit.c23pr492.talentease.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.c23pr492.talentease.data.AuthRepository
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.database.TalentEaseDatabase
import com.bangkit.c23pr492.talentease.data.datastore.AuthDataStore
import com.bangkit.c23pr492.talentease.data.network.ApiConfig
import com.google.firebase.auth.FirebaseAuth

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")
object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        return AuthRepository.getInstance(
            FirebaseAuth.getInstance(),
            AuthDataStore.getInstance(context.dataStore)
        )
    }

    fun provideRecruiterRepository(context: Context): RecruiterRepository {
        val database = TalentEaseDatabase.getDatabase(context)
        val dao = database.talentEaseDao()
        return RecruiterRepository.getInstance(
            ApiConfig.getApiService(),
            dao
        )
    }

    fun provideTalentRepository(context: Context): TalentRepository {
        val database = TalentEaseDatabase.getDatabase(context)
        val dao = database.talentEaseDao()
        return TalentRepository.getInstance(
            dao
        )
    }
}