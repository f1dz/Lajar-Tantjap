package `in`.khofid.lajartantjap.utils

import java.util.*

class TimeUtils {

    companion object {

        fun time(hour: Int, minute: Int): Calendar {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            return calendar
        }
    }
}