package com.mubarakansari.jetpack_notifications_android_kotlin

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private val keyReplay = "key_reply"
    private val channelId = "com.mubarakansari.jetpack_notifications_android_kotlin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(keyReplay).toString()
            tv_replay.text = inputString
        }

        //
        val notificationId = 45
        val replyNotification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.icon_instagram)
            .setContentText("Sending your Replay...")
            .setTimeoutAfter(3000)
            .build()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, replyNotification)
    }
}