package `in`.khofid.lajartantjap.utils

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.view.main.MainActivity
import `in`.khofid.lajartantjap.view.movie.MovieDetailActivity
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

        fun sendDailyNotification(context: Context, message: String, notifId: Int){
            val intent = Intent(context, MainActivity::class.java)
            notification(context, intent, message, notifId)
        }

        fun notification(context: Context, intent: Intent, message: String, notifId: Int) {

            var pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            var builder: NotificationCompat.Builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie))
                .setContentTitle(context.resources.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
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
                notificationManager.notify(notifId, notification)
            }
        }

        fun sendNewReleaseNotification(context: Context, movies: List<Movie>){
            var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var builder: NotificationCompat.Builder


            if(movies.count() < Constants.MAX_NOTIF){
                movies.forEach {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_MOVIE, it)
                    notification(context, intent, context.getString(R.string.new_release_notification, it.title), it.id!!)
                }
            } else {

                var inboxStyle = NotificationCompat.InboxStyle()
                    .setBigContentTitle(movies.count().toString() + " new releases")
                    .setSummaryText(context.getString(R.string.app_name))

                movies.forEach {
                    inboxStyle.addLine(it.title)
                }

                var intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_MOVIE, movies.first())
                var pendingIntent = PendingIntent.getActivity(context, Constants.STACK_NOTIF_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                builder = NotificationCompat.Builder(context, Constants.STACK_CHANNEL_ID)
                    .setContentTitle(movies.count().toString() + " new releases")
                    .setSmallIcon(R.drawable.ic_movie)
                    .setGroup(Constants.GROUP_STACK_NOTIF)
                    .setContentIntent(pendingIntent)
                    .setGroupSummary(true)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    var channel = NotificationChannel(
                        Constants.STACK_CHANNEL_ID,
                        Constants.STACK_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    builder.setChannelId(Constants.STACK_CHANNEL_ID)

                    if (notificationManager != null)
                        notificationManager.createNotificationChannel(channel)
                }

                var notification = builder.build()
                if (notificationManager != null)
                    notificationManager.notify(Constants.STACK_NOTIF_ID, notification)

            }

        }
    }
}