package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.data.database.TalentEaseDao
import com.bangkit.c23pr492.talentease.data.database.TalentEntity
import com.bangkit.c23pr492.talentease.utils.Const.tagRepository
import com.bangkit.c23pr492.talentease.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TalentRepository(
    private val mTalentEaseDao: TalentEaseDao,
) {
    suspend fun addTalent(talent: TalentEntity) {
        mTalentEaseDao.upsertTalent(talent)
    }

    suspend fun removeTalent(talent: TalentEntity) {
        mTalentEaseDao.upsertTalent(talent)
    }

    fun getAllPosition() = flow {
        emit(Resource.Loading)
        try {
            val response = mTalentEaseDao.getAllPosition()
            addTalent(TalentEntity.talent1)
            Log.d(tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun searchPositionFromName(query: String) = flow {
        emit(Resource.Loading)
        try {
            val response = mTalentEaseDao.searchPositionsFromName(query)
            Log.d(tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun getPositionWithPositionId(positionId: String) = flow {
        emit(Resource.Loading)
        try {
            val response = mTalentEaseDao.getPositionWithPositionId(positionId)
            Log.d(tagRepository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(tagRepository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: TalentRepository? = null
        fun getInstance(
            mTalentEaseDao: TalentEaseDao
        ): TalentRepository = instance ?: synchronized(this) {
            instance ?: TalentRepository(mTalentEaseDao)
        }.also { instance = it }
    }
}