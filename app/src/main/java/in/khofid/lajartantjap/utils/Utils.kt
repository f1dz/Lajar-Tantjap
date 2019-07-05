package `in`.khofid.lajartantjap.utils

fun String.getYear(): String {
    return this.substring(0,4)
}

fun String.getLanguageFormat(): String {
    if(this == "in")
        return "id-ID"
    return "${this}-${this.toUpperCase()}"
}