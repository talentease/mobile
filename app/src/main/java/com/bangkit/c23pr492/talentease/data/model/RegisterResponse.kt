package com.bangkit.c23pr492.talentease.data.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("data")
    val data: Register,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)

data class Register(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("role")
    val role: String,
)

