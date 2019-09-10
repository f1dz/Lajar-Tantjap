package `in`.khofid.lajartantjap.view.setting

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.services.MovieDailyReceiver
import `in`.khofid.lajartantjap.services.MovieNewReleaseReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.provider.Settings

class SettingFragment: PreferenceFragment(), Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private var dailyReminder = MovieDailyReceiver()
    private var newReleaseReminder = MovieNewReleaseReceiver()
    private lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting)
        if(activity != null){
            findPreference(getString(R.string.daily_reminder_key)).setOnPreferenceChangeListener(this)
            findPreference(getString(R.string.new_release_key)).setOnPreferenceChangeListener(this)
            findPreference(getString(R.string.locale_setting_key)).setOnPreferenceClickListener(this)
        }
    }

    override fun onPreferenceChange(preference: Preference, component: Any?): Boolean {
        val key = preference.key
        val isChecked = component as Boolean

        when (key) {
            getString(R.string.daily_reminder_key) -> if (isChecked) dailyReminder.setRepeatingAlarm(ctx) else dailyReminder.cancelRepetingAlarm(ctx)
            getString(R.string.new_release_key) -> if (isChecked) newReleaseReminder.setReminder(ctx!!) else newReleaseReminder.cancelReminder(ctx)
        }

        return true
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        val key = preference.key
        if(key.equals(getString(R.string.locale_setting_key))) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            return true
        }

        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
}