package com.bangkit.c23pr492.talentease.data.model.application

import com.google.gson.annotations.SerializedName

data class ApplicationByPositionModel(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("candidate")
	val candidate: Candidate,

	@field:SerializedName("cv")
	val cv: String,

	@field:SerializedName("positionId")
	val positionId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("position")
	val position: Position,

	@field:SerializedName("candidateId")
	val candidateId: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Company(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Position(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("companyId")
	val companyId: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("company")
	val company: Company,

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

data class Candidate(

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
