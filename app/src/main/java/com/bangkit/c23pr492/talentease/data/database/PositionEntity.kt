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
) {
    companion object {
        val position1 = PositionEntity(
            "1",
            "Associate Software Engineer",
            "Develop, collaborate, and fix software issues. Requires a Bachelor's degree in Computer Science or related field, proficiency in back-end programming languages, and strong problem-solving skills. Join our team for impactful software development.",
            "WFO",
            "1",
            "End of the world 💀",
            "6000000"
        )
    }
}

data class PositionWithCompany(
    @Embedded
    val position: PositionEntity,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "companyId"
    )
    val company: CompanyEntity
)