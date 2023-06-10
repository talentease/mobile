package com.bangkit.c23pr492.talentease.data.database

import androidx.room.*

@Entity(tableName = "tb_position")
data class PositionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "positionId")
    val positionId: String,

    @ColumnInfo(name = "name")
    val positionName: String,

    @ColumnInfo(name = "description")
    val positionDescription: String,

    @ColumnInfo(name = "type")
    val positionType: String,

    @ColumnInfo(name = "companyId")
    val companyId: String,

    @ColumnInfo(name = "deadline")
    val positionDeadline: String,

    @ColumnInfo(name = "salary")
    val positionSalary: String,
)

data class PositionWithCompany(
    @Embedded
    val position: PositionEntity,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "companyId"
    )
    val company: CompanyEntity
)