package com.bangkit.c23pr492.talentease.data

import com.bangkit.c23pr492.talentease.data.database.TalentEaseDao

class TalentRepository(
    private val mTalentEaseDao: TalentEaseDao,
) {

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