package com.bangkit.c23pr492.talentease.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getRole(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ROLE_KEY]
        }
    }

    suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }

    suspend fun clearRole() {
        dataStore.edit { preferences ->
            preferences.remove(ROLE_KEY)
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ROLE_KEY = stringPreferencesKey("role")
        @Volatile
        private var INSTANCE: AuthDataStore? = null
        fun getInstance(dataStore: DataStore<Preferences>): AuthDataStore {
            return INSTANCE ?: synchronized(this){
                val instance = AuthDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}