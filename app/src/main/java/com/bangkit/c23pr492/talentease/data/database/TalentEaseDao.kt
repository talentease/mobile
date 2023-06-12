package com.bangkit.c23pr492.talentease.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TalentEaseDao {
//    @Upsert
//    suspend fun upsertAll(beers: List<UserEntity>)

    // Admin
    @Upsert
    suspend fun upsertCompany(company: CompanyEntity)

    @Delete
    suspend fun deleteCompany(company: CompanyEntity)

    // General
    @Upsert
    suspend fun upsertApplication(application: ApplicationEntity)

    @Delete
    suspend fun deleteApplication(application: ApplicationEntity)

    // Recruiter

    @Upsert
    suspend fun upsertPosition(position: PositionEntity)

    @Delete
    suspend fun deletePosition(position: PositionEntity)

    @Transaction
    @Query("SELECT * from tb_application WHERE tb_application.companyId = :companyId")
    fun getAllApplicationWithCompanyId(companyId: String): Flow<List<ApplicationEntity>>

    @Transaction
    @Query("SELECT * from tb_application WHERE tb_application.applicationId = :applicationId")
    fun getApplicationWithApplicationId(applicationId: String): Flow<List<ApplicationWithTalentAndPositionAndCompany>>

    @Transaction
    @Query("SELECT * from tb_position WHERE tb_position.companyId = :companyId")
    fun getAllPositionWithCompanyId(companyId: String): Flow<List<PositionWithCompany>>


    // Talent
    @Upsert
    suspend fun upsertTalent(talent: TalentEntity)

    @Delete
    suspend fun deleteTalent(talent: TalentEntity)

    @Query("SELECT * from tb_talent")
    fun getTalent(): Flow<TalentEntity>

    @Transaction
    @Query("SELECT * from tb_position")
    fun getAllPosition(): Flow<List<PositionWithCompany>>

    @Transaction
    @Query("SELECT * from tb_position WHERE tb_position.name LIKE :query")
    fun searchPositionsFromName(query: String): Flow<List<PositionWithCompany>>

    @Transaction
    @Query("SELECT * from tb_position WHERE tb_position.positionId = :companyId")
    fun getPositionWithPositionId(companyId: String): Flow<PositionWithCompany>

    @Transaction
    @Query("SELECT * from tb_application WHERE tb_application.talentId = :talentId")
    fun getAllTalentApplicationWithTalentId(talentId: String): Flow<List<ApplicationWithTalentAndPositionAndCompany>>

    @Transaction
    @Query("SELECT * from tb_application WHERE tb_application.applicationId = :applicationId")
    fun getTalentApplicationWithApplicationId(applicationId: String): Flow<ApplicationWithTalentAndPositionAndCompany>


//    @Query("SELECT * FROM contact ORDER BY firstName ASC")
//    fun getContactsOrderedByFirstName(): Flow<List<Contact>>
//
//    @Query("SELECT * FROM contact ORDER BY lastName ASC")
//    fun getContactsOrderedByLastName(): Flow<List<Contact>>
//
//    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
//    fun getContactsOrderedByPhoneNumber(): Flow<List<Contact>>
//    @Transaction
//    @Query("SELECT * from tb_application WHERE tb_application.talentId = :talentId")
//    fun getAllApplicationWithTalentId(talentId: String): LiveData<List<ApplicationEntity>>

//    @Transaction
//    @Query("SELECT * from student")
//    fun getAllApplicationWithPosition(): LiveData<List<StudentAndUniversity>>
//
//    @Transaction
//    @Query("SELECT * from student")
//    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>>
//
//    @Query("DELETE FROM user")
//    suspend fun clearAll()
}