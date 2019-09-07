package `in`.khofid.lajartantjap.utils

import android.view.View

fun String.getYear(): String {
    if(this.isNotEmpty())
        return this.substring(0, 4)
    else return "Not Set"
}

fun String.getLanguageFormat(): String {
    if (this == "in")
        return "id-ID"
    return "${this}-${this.toUpperCase()}"
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}