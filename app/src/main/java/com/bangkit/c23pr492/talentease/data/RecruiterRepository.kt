package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.data.model.ApplicationModel
import com.bangkit.c23pr492.talentease.data.model.ApplicationsData
import com.bangkit.c23pr492.talentease.data.model.PositionItemModel
import com.bangkit.c23pr492.talentease.data.model.PositionListModel
import com.bangkit.c23pr492.talentease.data.network.ApiService
import com.bangkit.c23pr492.talentease.utils.Const
import com.bangkit.c23pr492.talentease.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecruiterRepository(
    private val apiService: ApiService
) {
    fun getAllApplications(): Flow<Resource<List<ApplicationModel>>> = flow {
        emit(Resource.Loading)
        try {
            val response = ApplicationsData.listApplicationData
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun searchApplications(query: String): Flow<Resource<List<ApplicationModel>>> = flow {
        emit(Resource.Loading)
        try {
            val response = ApplicationsData.listApplicationData.filter {
                it.name.contains(query, ignoreCase = true)
            }
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllPositions(token: String): Flow<Resource<PositionListModel>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getAllPositions(generateBearerToken(token))
            Log.d(Const.tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Const.tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun searchPositions(token: String, query: String): Flow<Resource<List<PositionItemModel>?>> =
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
            apiService: ApiService
        ): RecruiterRepository = instance ?: synchronized(this) {
            instance ?: RecruiterRepository(apiService)
        }.also { instance = it }
    }
}