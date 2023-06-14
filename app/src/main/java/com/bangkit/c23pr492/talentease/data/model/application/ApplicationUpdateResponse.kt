package com.bangkit.c23pr492.talentease.data.model.application

import com.google.gson.annotations.SerializedName

data class ApplicationUpdateResponse(

	@field:SerializedName("data")
	val data: ApplicationUpdateData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class ApplicationUpdateData(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
