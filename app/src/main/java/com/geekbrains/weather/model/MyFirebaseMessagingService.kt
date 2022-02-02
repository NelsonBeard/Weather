package com.geekbrains.weather.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.geekbrains.weather.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val TAG = "MyFirebaseMessaging"
const val CHANNEL_ID = "Default"
const val NOTIFICATION_ID = 42

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived $message")
        super.onMessageReceived(message)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Новое сообщение")
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "My channel",
                    NotificationManager.IMPORTANCE_DEFAULT,
                )
            )
            notificationBuilder.setChannelId(CHANNEL_ID)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken $token")
        super.onNewToken(token)
    }
}

