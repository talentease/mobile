package com.bangkit.c23pr492.talentease.data.model.position

import com.google.gson.annotations.SerializedName

data class PositionResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("companyId")
	val companyId: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("salary")
	val salary: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("deadline")
	val deadline: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
