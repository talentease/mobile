package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.data.datastore.AuthDataStore
import com.bangkit.c23pr492.talentease.utils.Const.tagRepository
import com.bangkit.c23pr492.talentease.utils.UiText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val authDataStore: AuthDataStore
) {
    fun getToken(): Flow<String?> = authDataStore.getToken()

    suspend fun saveToken(token: String) {
        authDataStore.saveToken(token)
    }

    private suspend fun clearToken() {
        authDataStore.clearToken()
    }

    fun getRole(): Flow<String?> = authDataStore.getRole()

    suspend fun saveRole(role: String) {
        authDataStore.saveRole(role)
    }

    private suspend fun clearRole() {
        authDataStore.clearRole()
    }

    fun checkRole(token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            Log.d(tagRepository, "checkRole: $token")
            val response = "recruiter"
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }

    fun loginUser(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading)
        try {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(response.user))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading)
        try {
            val response = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(response.user))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun logoutUser(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val response = firebaseAuth.signOut().also {
                clearToken()
            }
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            firebaseAuth: FirebaseAuth,
            authDataStore: AuthDataStore
        ): AuthRepository = instance ?: synchronized(this) {
            instance ?: AuthRepository(firebaseAuth, authDataStore)
        }.also { instance = it }
    }
}