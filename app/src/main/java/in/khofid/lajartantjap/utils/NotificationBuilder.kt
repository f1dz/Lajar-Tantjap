package `in`.khofid.lajartantjap.utils

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.view.main.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat

class NotificationBuilder(var context: Context) {

    companion object {

        fun sendNotification(context: Context) {

            var intent = Intent(context, MainActivity::class.java)
            var pendingIntent = PendingIntent.getActivity(context, Constants.ALARM_REPEATING_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            var builder: NotificationCompat.Builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie))
                .setContentTitle(context.resources.getString(R.string.app_name))
                .setContentText(context.resources.getString(R.string.notification_content))
                .setSubText(context.resources.getString(R.string.sub_text))
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(alarmSound)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var channel = NotificationChannel(
                    Constants.CHANNEL_ID,
                    Constants.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableVibration(true)
                channel.setVibrationPattern(longArrayOf(1000, 1000, 1000, 1000, 1000))
                channel.enableLights(true)

                builder.setChannelId(Constants.CHANNEL_ID)
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel)
                }
            }

            var notification = builder.build()

            if (notificationManager != null) {
                notificationManager.notify(Constants.NOTIFICATION_ID, notification)
            }
        }
    }
}