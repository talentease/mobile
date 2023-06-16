package com.bangkit.c23pr492.talentease.data.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
