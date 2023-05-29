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

class Repository(
    private val firebaseAuth: FirebaseAuth,
    private val authDataStore: AuthDataStore
) {
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

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            firebaseAuth: FirebaseAuth,
            authDataStore: AuthDataStore
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(firebaseAuth, authDataStore)
        }.also { instance = it }
    }
}