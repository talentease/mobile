package com.bangkit.c23pr492.talentease.data.model

import com.google.gson.annotations.SerializedName

data class CurriculumVitaeModel(
    @field:SerializedName("name")
    var name: String = "",

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("companyId")
    var contact: List<String> = listOf("https://www.linkedin.com/in/fnakhsan/"),

    @field:SerializedName("summary")
    var summary: String? = null,

    @field:SerializedName("experience")
    var experience: String? = null,

    @field:SerializedName("education")
    var education: String? = null,

    @field:SerializedName("expertise")
    var expertise: String? = null,

    @field:SerializedName("award")
    var award: String? = null,

    @field:SerializedName("certification")
    var certification: String? = null,
)

data class Contact(
    @field:SerializedName("linkedin")
    var linkedIn: String = "",

    @field:SerializedName("email")
    var email: String? = null
)
