package com.education.android.mainquizapp_final

import androidx.lifecycle.ViewModel

class QuestionListViewModel : ViewModel() {

    private val questionRepository = QuestionRepository.get()
    val questionListLiveData = questionRepository?.getQuestions()

    fun addQuestion(question: Question) {
        questionRepository?.addQuestion(question)
    }
}