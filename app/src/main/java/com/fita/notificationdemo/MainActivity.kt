package com.fita.notificationdemo


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MainActivity : AppCompatActivity() {
    var buttonTriggerNotification: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        buttonTriggerNotification = findViewById<View>(R.id.buttonTriggerNotification) as Button
        buttonTriggerNotification!!.setOnClickListener { triggerNotification() }
    }

    private fun createNotificationChannel() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val notificationChannel =  NotificationChannel(getString(R.string.NEWS_CHANNEL_ID),
            getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT)

             notificationChannel.description = getString(R.string.CHANNEL_DESCRIPTION)

             val notificationManager = getSystemService(NotificationManager::class.java)

             notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun triggerNotification() {
        val intent = Intent(this, NotificationDetailsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Notification Title")
                .setContentText("This is text, that will be shown as part of notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true)

        val notificationManagerCompat = NotificationManagerCompat.from(this)

        notificationManagerCompat.notify(1, builder.build() )

    }
}