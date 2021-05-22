package com.education.android.mainquizapp_final.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.education.android.mainquizapp_final.Question

@Database(entities = [ Question::class ], version = 1)
@TypeConverters(QuestionTypeConverters::class)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
}