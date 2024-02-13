package com.example.ttu

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Not: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    return@addOnCompleteListener
                }

            val token  = task.result
            Log.e("TAG","token -> $token")
        }
    }
}