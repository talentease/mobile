package com.bangkit.c23pr492.talentease.data.model.profile

import com.google.gson.annotations.SerializedName

data class CreateProfileModel(

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("location")
	val location: String
)
