package ir.ha.practice.utility.util

fun convertTimeToMilliSecond(time: Long): Long {
    return time * 1000L
}

fun convertTimeToSecond(time: Long): Long {
    return time / 1000L
}
