package `in`.khofid.lajartantjap.services

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.DateTimeUtils
import `in`.khofid.lajartantjap.utils.NotificationBuilder
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MovieDailyReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationBuilder.sendDailyNotification(context, context.getString(R.string.notification_content), Constants.NOTIFICATION_ID)
    }

    fun setRepeatingAlarm(context: Context) {
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var timeAlarm = DateTimeUtils.time(Constants.ALARM_HOUR, Constants.ALARM_MINUTE)

        if(alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAlarm.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent(context))
        }
    }

    fun cancelRepetingAlarm(context: Context) {
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent(context))
    }

    fun pendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MovieDailyReceiver::class.java)
        return PendingIntent.getBroadcast(context, Constants.ALARM_REPEATING_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
}