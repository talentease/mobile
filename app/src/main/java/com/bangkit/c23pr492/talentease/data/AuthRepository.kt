package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.data.datastore.AuthDataStore
import com.bangkit.c23pr492.talentease.data.network.ApiService
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
    private val authDataStore: AuthDataStore,
    private val apiService: ApiService
) {
    fun getToken(): Flow<String?> = authDataStore.getToken()

    suspend fun saveToken(token: String) {
        authDataStore.saveToken(token)
    }

    private suspend fun clearToken() {
        authDataStore.clearToken()
    }

//    fun getUserId(): Flow<String?> = authDataStore.getUserId()
//
//    suspend fun saveUserId(uid: String) {
//        authDataStore.saveUserId(uid)
//    }
//
//    private suspend fun clearUserId() {
//        authDataStore.clearUserId()
//    }

    fun getRole(): Flow<String?> = authDataStore.getRole()

    suspend fun saveRole(token: String) {
        authDataStore.saveRole(token)
    }

    private suspend fun clearRole() {
        authDataStore.clearRole()
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
                clearRole()
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

    fun getRoleById(token: String, uid: String) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getProfileById(token, uid)
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
            authDataStore: AuthDataStore,
            apiService: ApiService
        ): AuthRepository = instance ?: synchronized(this) {
            instance ?: AuthRepository(firebaseAuth, authDataStore, apiService)
        }.also { instance = it }
    }
}