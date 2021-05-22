package com.education.android.mainquizapp_final

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class QuestionDetailViewModel() : ViewModel() {

    private val questionRepository = QuestionRepository.get()
    private val questionIdLiveData = MutableLiveData<UUID>()

    val questionLiveData: LiveData<Question?> = Transformations.switchMap(questionIdLiveData) { questionId ->
        questionRepository?.getQuestion(questionId)
    }

    fun loadQuestion(questionId: UUID) {
        questionIdLiveData.value = questionId
    }

    fun saveQuestion(question: Question) {
        questionRepository?.updateQuestion(question)
    }
}