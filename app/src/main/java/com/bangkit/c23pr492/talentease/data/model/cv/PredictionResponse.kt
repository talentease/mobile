package com.bangkit.c23pr492.talentease.data.model.cv

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("skills")
	val skills: String? = null,

	@field:SerializedName("summary")
	val summary: String? = null
)
