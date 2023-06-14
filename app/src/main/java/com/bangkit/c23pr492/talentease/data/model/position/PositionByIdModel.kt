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

//data class Company1(
//
//	@field:SerializedName("createdAt")
//	val createdAt: String,
//
//	@field:SerializedName("address")
//	val address: String,
//
//	@field:SerializedName("name")
//	val name: String,
//
//	@field:SerializedName("description")
//	val description: String,
//
//	@field:SerializedName("updatedAt")
//	val updatedAt: String
//)
//
//data class Data1(
//
//	@field:SerializedName("createdAt")
//	val createdAt: String,
//
//	@field:SerializedName("companyId")
//	val companyId: String,
//
//	@field:SerializedName("description")
//	val description: String,
//
//	@field:SerializedName("company")
//	val company: Company1,
//
//	@field:SerializedName("id")
//	val id: String,
//
//	@field:SerializedName("title")
//	val title: String,
//
//	@field:SerializedName("salary")
//	val salary: Int,
//
//	@field:SerializedName("type")
//	val type: String,
//
//	@field:SerializedName("deadline")
//	val deadline: String,
//
//	@field:SerializedName("updatedAt")
//	val updatedAt: String
//)
