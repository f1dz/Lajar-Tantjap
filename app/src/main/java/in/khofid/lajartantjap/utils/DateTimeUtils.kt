package `in`.khofid.lajartantjap.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    companion object {

        fun time(hour: Int, minute: Int): Calendar {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            return calendar
        }

        fun currentDate(): String {
            var df = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
            var date = Date()

            return df.format(date)
        }
    }
}