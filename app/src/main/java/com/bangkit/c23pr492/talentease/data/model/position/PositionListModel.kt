package com.bangkit.c23pr492.talentease.data.model.position

import com.google.gson.annotations.SerializedName

data class PositionListModel(

	@field:SerializedName("data")
	val data: List<PositionItemModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class PositionItemModel(

	@field:SerializedName("id")
	val id: String = "",

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("companyId")
	val companyId: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("company")
	val company: Company? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("deadline")
	val deadline: String? = null,

	@field:SerializedName("salary")
	val salary: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

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
