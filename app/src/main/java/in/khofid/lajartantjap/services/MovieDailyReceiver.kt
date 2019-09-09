package `in`.khofid.lajartantjap.services

import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.NotificationBuilder
import `in`.khofid.lajartantjap.utils.TimeUtils
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MovieDailyReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationBuilder.sendNotification(context)
    }

    fun setRepeatingAlarm(context: Context) {
        var intent = Intent(context, MovieDailyReceiver::class.java)
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var timeAlarm = TimeUtils.time(Constants.ALARM_HOUR, Constants.ALARM_MINUTE)

        var pendingIntent = PendingIntent.getBroadcast(context, Constants.ALARM_REPEATING_ID, intent, 0)

        if(alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAlarm.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }
}