package com.example.intersvyaztest.presentation.feature.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.intersvyaztest.presentation.MainActivity
import com.example.itntersvyaztest.R

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val fromSymbol = intent.getStringExtra(FROM_SYMBOL_EXTRA)

        val mainActivityIntent = Intent(context, MainActivity::class.java)
            .apply {
                putExtra(FROM_SYMBOL_EXTRA, fromSymbol)
            }

        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID, mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(
            context, CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_alarm_add_24)
            .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
            .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_1"
        private const val TITLE_EXTRA = "title_extra"
        private const val MESSAGE_EXTRA = "message_extra"
        const val FROM_SYMBOL_EXTRA = "fromSymbol"
        const val TIME_1MIN = 6000L
        const val TIME_15MIN = 900000L
        const val TIME_1HOUR = 3600000L
        const val TIME_1DAY = 86400000L
        const val TIME_7DAYS = 604800000L

        private fun createNotification(context: Context, title: String, message: String) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, title, importance).apply {
                description = message
            }

            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        fun scheduleNotification(
            context: Context,
            fromSymbol: String,
            title: String,
            message: String,
            time: Long
        ) {
            createNotification(context, title, message)

            val intent = Intent(context, Notification::class.java).apply {
                putExtra(TITLE_EXTRA, title)
                putExtra(MESSAGE_EXTRA, message)
                putExtra(FROM_SYMBOL_EXTRA, fromSymbol)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                getTime(time),
                pendingIntent
            )
        }

        private fun getTime(time: Long): Long {
            return System.currentTimeMillis() + time
        }
    }
}