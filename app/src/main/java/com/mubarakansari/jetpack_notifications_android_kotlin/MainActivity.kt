package com.mubarakansari.jetpack_notifications_android_kotlin

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val channelId = "com.mubarakansari.jetpack_notifications_android_kotlin"
    private var notificationManager: NotificationManager? = null
    private val keyReplay = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotification(channelId, "Demo", "Hello Ansari")

        btn_notification.setOnClickListener {
            displayNotification()
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun displayNotification() {
        val notificationId = 45
        val detailsActivityIntent = Intent(this, DetailsActivity::class.java)
        val pendingDetailsIntent = PendingIntent.getActivity(
            this,
            0,
            detailsActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Actions Details on Notification
        val notificationDetailsActivityIntent =
            Intent(this, NotificationDetailsActivity::class.java)
        val pendingNotificationDetailsActivity = PendingIntent.getActivity(
            this,
            0,
            notificationDetailsActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val activityNotificationDetailsActivity: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Details", pendingNotificationDetailsActivity)
                .build()

        // Action Settings Activity
        val settingsActivityIntent = Intent(this, SettingsActivity::class.java)
        val pendingSettingsActivity = PendingIntent.getActivity(
            this,
            0,
            settingsActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionSettingsActivity =
            NotificationCompat.Action.Builder(0, "Setting", pendingSettingsActivity)
                .build()

        // Replay remote
        val remoteInput = RemoteInput.Builder(keyReplay).run {
            setLabel("Relay the Message")
            build()
        }
        val replayAction = NotificationCompat.Action.Builder(
            0,
            "Replay",
            pendingDetailsIntent
        ).addRemoteInput(remoteInput)
            .build()

        //Notification Builder
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Instagram Message")
            .setContentText("mr.ansari7 is message you")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.icon_instagram)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(activityNotificationDetailsActivity)
            .addAction(actionSettingsActivity)
            .addAction(replayAction)
//            .setTimeoutAfter(4000)
            .build()
        notificationManager?.notify(notificationId, notification)


    }

    private fun createNotification(id: String, name: String, channelDescription: String) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance).apply {
            description = channelDescription
        }
        notificationManager?.createNotificationChannel(channel)
    }
}