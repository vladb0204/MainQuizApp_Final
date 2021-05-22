package com.education.android.mainquizapp_final.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.education.android.mainquizapp_final.Question
import java.util.*

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question")
    fun getQuestions(): LiveData<List<Question>>

    @Query("SELECT * FROM question WHERE id=(:id)")
    fun getQuestion(id: UUID): LiveData<Question?>

    @Update
    fun updateQuestion(crime: Question)

    @Insert
    fun addQuestion(crime: Question)
}