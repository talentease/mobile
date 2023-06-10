package com.bangkit.c23pr492.talentease.data.database

import androidx.room.*

@Entity(tableName = "tb_company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "companyId")
    val companyId: String,

    @ColumnInfo(name = "name")
    val companyName: String,

    @ColumnInfo(name = "address")
    val companyAddress: String,

    @ColumnInfo(name = "description")
    val companyDescription: String,
)

data class CompanyWithApplication(
    @Embedded
    val company: CompanyEntity,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "companyId"
    )
    val application: List<ApplicationEntity>
)