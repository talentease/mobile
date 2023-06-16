package com.bangkit.c23pr492.talentease.data.network

import com.bangkit.c23pr492.talentease.data.model.application.*
import com.bangkit.c23pr492.talentease.data.model.cv.PredictionModel
import com.bangkit.c23pr492.talentease.data.model.cv.PredictionResponse
import com.bangkit.c23pr492.talentease.data.model.position.*
import com.bangkit.c23pr492.talentease.data.model.profile.CreateProfileModel
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

    @GET("position/{positionId}")
    suspend fun getPositionByPositionId(
        @Header("Authorization") token: String,
        @Path("positionId") id: String
    ): PositionByIdModel

    @GET("profile/{uid}")
    suspend fun getProfileById(
        @Header("Authorization") token: String,
        @Path("uid") id: String
    ): ProfileModel

    @PATCH("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body profile: CreateProfileModel
    ): ProfileModel

    // Recruiter
    @GET("application/position/{positionId}")
    suspend fun getApplicationByPositionId(
        @Header("Authorization") token: String,
        @Path("positionId") id: String
    ): ApplicationByPositionIdModel

    @GET("application/{applicationId}")
    suspend fun getApplicationById(
        @Header("Authorization") token: String,
        @Path("applicationId") id: String
    ): ApplicationByIdModel

    @PATCH("application/{userId}")
    suspend fun updateApplication(
        @Header("Authorization") token: String,
        @Path("userId") id: String,
        @Body status: StatusModel
    ): ApplicationUpdateResponse

    @POST("prediction")
    suspend fun summarizeCv(
        @Header("Authorization") token: String,
        @Body id: PredictionModel
    ): PredictionResponse

    @POST("position")
    suspend fun uploadPosition(
        @Header("Authorization") token: String,
        @Body position: PositionModel
    ): PositionResponse

    @PATCH("position/{positionId}")
    suspend fun updatePosition(
        @Header("Authorization") token: String,
        @Path("positionId") id: String,
        @Body position: PositionModel
    ): PositionResponse

    @DELETE("position/{positionId}")
    suspend fun deletePosition(
        @Header("Authorization") token: String,
        @Path("positionId") id: String
    ): DeletePositionResponse

    // Talent
    @Multipart
    @POST("application/create")
    suspend fun applyPositions(
        @Header("Authorization") token: String,
        @Part("positionId") positionId: RequestBody,
        @Part cv: MultipartBody.Part,
    ): ApplyApplicationResponse

    @POST("application/candidate")
    suspend fun createCandidateProfile(
        @Header("Authorization") token: String,
        @Body profile: CreateProfileModel
    ): ProfileModel

    @GET("application/user/{uid}")
    suspend fun getAllApplicationsByUserId(
        @Header("Authorization") token: String,
        @Path("uid") id: String
    ): AllApplicationByUserIdResponse
}