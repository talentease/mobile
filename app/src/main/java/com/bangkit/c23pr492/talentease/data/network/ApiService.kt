package com.bangkit.c23pr492.talentease.data.network

import com.bangkit.c23pr492.talentease.data.model.PositionListModel
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("position")
    suspend fun getAllPositions(
        @Header("Authorization") token: String
    ): PositionListModel


}