package com.bangkit.c23pr492.talentease.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String = "",

    @ColumnInfo(name = "public_repos")
    val repository: Int,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "company")
    val company: String?
)