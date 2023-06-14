package com.bangkit.c23pr492.talentease.data.model.position

import com.google.gson.annotations.SerializedName

data class PositionModel(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("salary")
	val salary: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("deadline")
	val deadline: String
)
