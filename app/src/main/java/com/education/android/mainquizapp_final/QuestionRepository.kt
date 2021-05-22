package com.education.android.mainquizapp_final

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors
import com.education.android.mainquizapp_final.database.QuestionDatabase

private const val DATABASE_NAME = "question-database"

class QuestionRepository private constructor(context: Context) {

    private val database: QuestionDatabase = Room.databaseBuilder(context.applicationContext,
        QuestionDatabase::class.java, DATABASE_NAME).build()
    private val questionDao = database.questionDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getQuestions(): LiveData<List<Question>> = questionDao.getQuestions()

    fun getQuestion(id: UUID): LiveData<Question?> = questionDao.getQuestion(id)

    fun updateQuestion(question: Question) {
        executor.execute { questionDao.updateQuestion(question) }
    }

    fun addQuestion(question: Question) {
        executor.execute { questionDao.addQuestion(question) }
    }

    companion object {
        private var INSTANCE: QuestionRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = QuestionRepository(context)
            }
        }

        fun get(): QuestionRepository? {
            return INSTANCE
        }
    }
}