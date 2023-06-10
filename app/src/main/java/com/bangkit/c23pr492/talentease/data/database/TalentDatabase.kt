package com.bangkit.c23pr492.talentease.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ApplicationEntity::class, CompanyEntity::class, PositionEntity::class, TalentEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class TalentEaseDatabase : RoomDatabase() {
    abstract fun talentEaseDao(): TalentEaseDao

    companion object {
        @Volatile
        private var INSTANCE: TalentEaseDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TalentEaseDatabase {
            if (INSTANCE == null) {
                synchronized(TalentEaseDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TalentEaseDatabase::class.java,
                        "favorite_db"
                    ).build()
                }
            }
            return INSTANCE as TalentEaseDatabase
        }
    }
}