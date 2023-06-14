package com.bangkit.c23pr492.talentease.data.model.application

import com.google.gson.annotations.SerializedName

data class ApplyApplicationResponse(

	@field:SerializedName("data")
	val data: ApplyApplicationData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class ApplyApplicationData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("cv")
	val cv: String,

	@field:SerializedName("positionId")
	val positionId: String,

	@field:SerializedName("id")
	val applicationId: String,

	@field:SerializedName("candidateId")
	val candidateId: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
