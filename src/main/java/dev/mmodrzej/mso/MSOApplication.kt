package dev.mmodrzej.mso

import android.app.Application

class MSOApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}