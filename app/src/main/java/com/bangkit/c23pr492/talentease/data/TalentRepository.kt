package com.bangkit.c23pr492.talentease.data

import android.util.Log
import com.bangkit.c23pr492.talentease.data.database.ApplicationEntity
import com.bangkit.c23pr492.talentease.data.database.TalentEaseDao
import com.bangkit.c23pr492.talentease.data.database.TalentEntity
import com.bangkit.c23pr492.talentease.data.datastore.AuthDataStore
import com.bangkit.c23pr492.talentease.utils.Const.tagRepository
import com.bangkit.c23pr492.talentease.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TalentRepository(
    private val mTalentEaseDao: TalentEaseDao,
    private val authDataStore: AuthDataStore
) {
    suspend fun addTalent(talent: TalentEntity) {
        mTalentEaseDao.upsertTalent(talent)
    }

    suspend fun removeTalent(talent: TalentEntity) {
        mTalentEaseDao.upsertTalent(talent)
    }

    suspend fun applyApplication() {
        mTalentEaseDao.upsertApplication(ApplicationEntity.application1)
    }

    fun getTalentId(): Flow<String?> = authDataStore.getTalentId()

    suspend fun saveTalentId(talentId: String) {
        authDataStore.saveTalentId(talentId)
    }

    private suspend fun clearTalentId() {
        authDataStore.clearTalentId()
    }

    fun getAllPosition() = flow {
        emit(Resource.Loading)
        try {
            val response = mTalentEaseDao.getAllPosition()
            addTalent(TalentEntity.talent1)
            saveTalentId(TalentEntity.talent1.talentId)
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

    fun getAllTalentApplicationWithTalentId(talentId: String) = flow {
        emit(Resource.Loading)
        try {
            val response = mTalentEaseDao.getAllTalentApplicationWithTalentId(talentId)
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
            mTalentEaseDao: TalentEaseDao,
            authDataStore: AuthDataStore
        ): TalentRepository = instance ?: synchronized(this) {
            instance ?: TalentRepository(mTalentEaseDao, authDataStore)
        }.also { instance = it }
    }
}