package com.example.ttu

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class Not: Application() {
    // Переменная для хранения токена
    private var fcmToken: String? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fcmToken = task.result
            } else {
                Log.e("Not", "Fetching FCM registration token failed", task.exception)
            }
        }
    }

    // Метод для получения токена извне
    fun getFcmToken(): String? {
        return fcmToken
    }
}
