package com.bangkit.c23pr492.talentease.data.database

import androidx.room.*

@Entity(tableName = "tb_application")
data class ApplicationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "applicationId")
    val applicationId: String,

    @ColumnInfo(name = "status", defaultValue = "Pending")
    val status: String,

    @ColumnInfo(name = "positionId")
    val positionId: String,

    @ColumnInfo(name = "companyId")
    val companyId: String,

    @ColumnInfo(name = "talentId")
    val talentId: String,
) {
    companion object {
        val application1 = ApplicationEntity(
            "1",
            "pending",
            "1",
            "1",
            "1"
        )
    }
}

data class ApplicationWithTalentAndPositionAndCompany(
    @Embedded
    val application: ApplicationEntity,
    @Relation(
        parentColumn = "talentId",
        entityColumn = "talentId"
    )
    val talent: TalentEntity,
    @Relation(
        parentColumn = "positionId",
        entityColumn = "positionId"
    )
    val position: PositionEntity,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "companyId"
    )
    val company: CompanyEntity
)
