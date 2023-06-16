package com.bangkit.c23pr492.talentease.data.model.application

import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.data.model.profile.Data
import com.google.gson.annotations.SerializedName

data class ApplicationWithProfileModel (
    @field:SerializedName("data")
    val data: List<ApplicationWithProfile>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)

data class ApplicationWithProfile (
    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("cv")
    val cv: String,

    @field:SerializedName("positionId")
    val positionId: String,

    val positionItemModel: PositionItemModel,

    @field:SerializedName("id")
    val id: String,

    val data: Data,

    @field:SerializedName("candidateId")
    val candidateId: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)