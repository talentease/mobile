package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.data.datastore.AuthDataStore
import com.bangkit.c23pr492.talentease.data.model.cv.PredictionModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionModel
import com.bangkit.c23pr492.talentease.data.model.position.StatusModel
import com.bangkit.c23pr492.talentease.data.model.profile.CreateProfileModel
import com.bangkit.c23pr492.talentease.data.network.ApiService
import com.bangkit.c23pr492.talentease.utils.Const
import com.bangkit.c23pr492.talentease.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecruiterRepository(
    private val apiService: ApiService,
    private val mlService: ApiService,
    private val authDataStore: AuthDataStore
) {
    fun getRecruiterId(): Flow<String?> = authDataStore.getUserId()

    fun getApplicationByPositionId(token: String, positionId: String) = flow {
        emit(Resource.Loading)
        try {
            val response =
                apiService.getApplicationByPositionId(generateBearerToken(token), positionId)
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }

    fun getDetailApplicationById(token: String, applicationId: String) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getApplicationById(generateBearerToken(token), applicationId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }

    fun updateApplication(token: String, userId: String, status: StatusModel) = flow {
        emit(Resource.Loading)
        try {
            val response =
                apiService.updateApplication(generateBearerToken(token), userId, status)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }

    fun summarizeCv(token: String, id: PredictionModel) = flow {
        emit(Resource.Loading)
        try {
            val response = mlService.summarizeCv(generateBearerToken(token), id)
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllPositions(token: String): Flow<Resource<List<PositionItemModel>?>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getAllPositions(generateBearerToken(token)).data?.toList()
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun searchPositionsFromName(
        token: String,
        query: String
    ): Flow<Resource<List<PositionItemModel>?>> =
        flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getAllPositions(generateBearerToken(token)).data?.toList()
                    ?.filter {
                        it.title?.contains(query, ignoreCase = true) ?: false
                    }
                Log.d(Const.tagRepository, response.toString())
                emit(Resource.Success(response))
            } catch (e: Exception) {
                Log.e(Const.tagRepository, Log.getStackTraceString(e))
                emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
            }
        }.flowOn(Dispatchers.IO)

    fun getPositionByPositionID(token: String, positionId: String) = flow {
        emit(Resource.Loading)
        try {
            val response =
                apiService.getPositionByPositionId(generateBearerToken(token), positionId)
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response.data))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun uploadPosition(token: String, position: PositionModel) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.uploadPosition(generateBearerToken(token), position)
            Log.d(Const.tagRepository, "upload: $response")
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, "upload: ${Log.getStackTraceString(e)}")
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun updatePosition(token: String, positionId: String, position: PositionModel) = flow {
        emit(Resource.Loading)
        try {
            val response =
                apiService.updatePosition(generateBearerToken(token), positionId, position)
            Log.d(Const.tagRepository, "update: $response")
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, "update: ${Log.getStackTraceString(e)}")
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun deletePosition(token: String, positionId: String) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.deletePosition(generateBearerToken(token), positionId)
            Log.d(Const.tagRepository, "delete: $response")
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, "update: ${Log.getStackTraceString(e)}")
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }

    fun createProfile(token: String, profile: CreateProfileModel) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.createCandidateProfile(generateBearerToken(token), profile)
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response.data))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, "upload " + Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun updateProfile(token: String, profile: CreateProfileModel) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.updateProfile(generateBearerToken(token), profile)
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, "upload " + Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }

    fun getProfileById(token: String, uid: String) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getProfileById(token, uid)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun generateBearerToken(token: String): String {
        return if (token.contains("bearer", true)) {
            token
        } else {
            "Bearer $token"
        }
    }

    companion object {
        @Volatile
        private var instance: RecruiterRepository? = null
        fun getInstance(
            apiService: ApiService,
            mlService: ApiService,
            authDataStore: AuthDataStore
        ): RecruiterRepository = instance ?: synchronized(this) {
            instance ?: RecruiterRepository(apiService, mlService, authDataStore)
        }.also { instance = it }
    }
}