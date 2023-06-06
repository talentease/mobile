package com.bangkit.c23pr492.talentease.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertAll(beers: List<UserEntity>)

    @Query("SELECT * FROM user")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user")
    suspend fun clearAll()
}