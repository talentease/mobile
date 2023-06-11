package com.bangkit.c23pr492.talentease.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_talent")
data class TalentEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "talentId")
    val talentId: String,

    @ColumnInfo(name = "name")
    val talentName: String,

    @ColumnInfo(name = "age")
    val talentAge: String,

    @ColumnInfo(name = "gender")
    val talentGender: String,

    @ColumnInfo(name = "location")
    val talentLocation: String,

    @ColumnInfo(name = "contact")
    val talentContact: String,

    @ColumnInfo(name = "experience")
    val talentExperience: String?,

    @ColumnInfo(name = "education")
    val talentEducation: String?,

    @ColumnInfo(name = "award")
    val talentAward: String?,

    @ColumnInfo(name = "certificate")
    val talentCertificate: String?,
) {
    companion object {
        val talent1 = TalentEntity(
            "1",
            "John",
            "20",
            "Male",
            "Sleman",
            "John@gmail.com",
            "1 Year as a Software Engineer at META",
            "Bachelor's degree, Computer Science at UBLO",
            null,
            "Belajar Fundamental Aplikasi Android"
        )
    }
}
