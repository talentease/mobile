package com.bangkit.c23pr492.talentease.data.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileModel(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class Data(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("updateAt")
	val updateAt: String,

	@field:SerializedName("email")
	val email: String
)
