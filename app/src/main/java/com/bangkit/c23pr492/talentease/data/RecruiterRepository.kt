package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.data.database.TalentEaseDao
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionModel
import com.bangkit.c23pr492.talentease.data.network.ApiService
import com.bangkit.c23pr492.talentease.utils.Const
import com.bangkit.c23pr492.talentease.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecruiterRepository(
    private val apiService: ApiService,
    private val mTalentEaseDao: TalentEaseDao
) {
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
            mTalentEaseDao: TalentEaseDao
        ): RecruiterRepository = instance ?: synchronized(this) {
            instance ?: RecruiterRepository(apiService, mTalentEaseDao)
        }.also { instance = it }
    }
}