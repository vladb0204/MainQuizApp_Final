package com.education.android.mainquizapp_final

import android.app.Application

class QuestionIntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        QuestionRepository.initialize(this)
    }
}