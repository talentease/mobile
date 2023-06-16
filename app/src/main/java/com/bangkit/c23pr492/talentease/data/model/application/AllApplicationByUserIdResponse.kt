package com.bangkit.c23pr492.talentease.data.model.application

import com.google.gson.annotations.SerializedName

data class AllApplicationByUserIdResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
