package com.bangkit.c23pr492.talentease.data.model.position

import com.google.gson.annotations.SerializedName

data class PositionByIdModel(

	@field:SerializedName("data")
	val data: PositionItemModel,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
