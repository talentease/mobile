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
) {
    companion object {
        val company1 = CompanyEntity(
            "1",
            "Google Indonesia",
            "SCBD, Pacific Century Place Tower Level 45, Jl. Jenderal Sudirman No.53, RT.5/RW.3, Senayan, Kebayoran Baru, South Jakarta City, Jakarta 12190",
            "Google LLC is an American multinational technology company focusing on artificial intelligence, online advertising, search engine technology, cloud computing, computer software, quantum computing, e-commerce, and consumer electronics.",
        )
    }
}

data class CompanyWithApplication(
    @Embedded
    val company: CompanyEntity,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "companyId"
    )
    val application: List<ApplicationEntity>
)