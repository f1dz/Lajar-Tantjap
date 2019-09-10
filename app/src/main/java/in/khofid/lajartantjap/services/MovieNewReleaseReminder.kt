package `in`.khofid.lajartantjap.services

import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.SettingPresenter
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.DateTimeUtils
import `in`.khofid.lajartantjap.utils.NotificationBuilder
import `in`.khofid.lajartantjap.view.setting.SettingCallback
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MovieNewReleaseReminder: BroadcastReceiver(), SettingCallback {

    private var mMovies: MutableList<Movie> = mutableListOf()

    override fun onReceive(context: Context, intent: Intent) {
        var settingPresenter = SettingPresenter(context, this)
        settingPresenter.getNewReleaseMovies(DateTimeUtils.currentDate())
    }

    fun setNewReleaseReminder(context: Context) {
        var intent = Intent(context, MovieNewReleaseReminder::class.java)
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var timeAlarm = DateTimeUtils.time(Constants.ALARM_NEW_RELEASE_HOUR, Constants.ALARM_NEW_RELEASE_MINUTE)

        var pendingIntent = PendingIntent.getBroadcast(context, Constants.ALARM_NEW_RELEASE_ID, intent, 0)

        if(alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAlarm.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun cancelNewReleaseReminder(context: Context) {
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent(context))
    }

    fun pendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MovieNewReleaseReminder::class.java)
        return PendingIntent.getBroadcast(context, Constants.ALARM_NEW_RELEASE_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    override fun getNewReleaseMovieList(context: Context, movies: List<Movie>) {
        mMovies.clear()
        mMovies.addAll(movies)
        NotificationBuilder.sendNewReleaseNotification(context, movies)
    }
}