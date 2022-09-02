package com.qi.pushnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val title: String? = message.notification?.title
        val body: String? = message.notification?.body

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            generateNotification(title, body)
        }
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyTag", "Refreshed token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateNotification(title: String?, body: String?) {
        val channel = NotificationChannel(
            getString(R.string.channel_id),
            getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
        val notification: Notification.Builder =
            Notification.Builder(this, getString(R.string.channel_id))
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(1, notification.build())
    }
}