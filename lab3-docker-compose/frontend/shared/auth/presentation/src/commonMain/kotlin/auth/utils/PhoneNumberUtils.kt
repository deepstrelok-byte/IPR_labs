package auth.utils

fun String.clearedPhoneNumber(): String =
    this
        .replace(" ", ".")
        .replace("(", ".")
        .replace(")", ".")
        .replace(".", "")


val CLEAR_PHONE_REGEX_PATTERN = Regex("^\\+79[0-9]{9}\$")