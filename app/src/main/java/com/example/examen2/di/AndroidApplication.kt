package com.example.examen2.di

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}