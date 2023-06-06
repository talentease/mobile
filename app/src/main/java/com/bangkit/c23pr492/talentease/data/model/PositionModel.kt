package com.bangkit.c23pr492.talentease.data.model

import com.google.gson.annotations.SerializedName

data class PositionModel(

	@field:SerializedName("id")
	val id: String = "",

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("companyID")
	val companyID: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("salary")
	val salary: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("deadline")
	val deadline: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
