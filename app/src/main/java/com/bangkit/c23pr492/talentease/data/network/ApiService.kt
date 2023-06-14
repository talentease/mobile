package com.bangkit.c23pr492.talentease.data.network

import com.bangkit.c23pr492.talentease.data.model.application.ApplicationModel
import com.bangkit.c23pr492.talentease.data.model.application.ApplicationUpdateResponse
import com.bangkit.c23pr492.talentease.data.model.application.ApplyApplicationResponse
import com.bangkit.c23pr492.talentease.data.model.position.*
import com.bangkit.c23pr492.talentease.data.model.profile.ProfileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    // General
    @GET("position")
    suspend fun getAllPositions(
        @Header("Authorization") token: String
    ): PositionListModel

    @GET("position")
    suspend fun getPositionByPositionId(
        @Header("Authorization") token: String,
        @Path("positionId") id: String
    ): PositionItemModel

    @GET("profile")
    suspend fun getProfileById(
        @Header("Authorization") token: String,
        @Path("uid") id: String
    ): ProfileModel

    // Recruiter
    @GET("application")
    suspend fun getApplicationByPositionId(
        @Header("Authorization") token: String,
        @Path("positionId") id: String
    ): ApplicationModel

    @PATCH("application")
    suspend fun updateApplication(
        @Header("Authorization") token: String,
        @Body status: StatusModel
    ): ApplicationUpdateResponse

    @POST("position")
    suspend fun uploadPosition(
        @Header("Authorization") token: String,
        @Body position: PositionModel
    ): PositionResponse

    // Talent
    @Multipart
    @POST("application/create")
    suspend fun applyPositions(
        @Header("Authorization") token: String,
        @Part("positionId") positionId: RequestBody,
        @Part file: MultipartBody.Part?,
    ): ApplyApplicationResponse
}