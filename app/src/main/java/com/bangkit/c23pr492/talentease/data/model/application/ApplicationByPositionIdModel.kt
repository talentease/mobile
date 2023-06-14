package com.bangkit.c23pr492.talentease.data.model.application

import com.google.gson.annotations.SerializedName

data class ApplicationByPositionIdModel(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("candidate")
	val candidate: Candidate? = null,

	@field:SerializedName("cv")
	val cv: String? = null,

	@field:SerializedName("positionId")
	val positionId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("position")
	val position: Position? = null,

	@field:SerializedName("candidateId")
	val candidateId: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Position(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("companyId")
	val companyId: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("company")
	val company: Company? = null,

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

data class Company(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Candidate(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("updateAt")
	val updateAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
