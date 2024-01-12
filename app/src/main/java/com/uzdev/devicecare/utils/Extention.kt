package com.uzdev.devicecare.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
fun Long.convertLongToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}

fun Long.getDurationBreakdown(): String {
    var millis = this
    require(millis >= 0) { "Duration must be greater than zero!" }
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    return "$hours h $minutes m $seconds s"
}

fun Long.getSpaceAsGb(): String {
    var mSize = this
    var ext: String? = null
    if (mSize >= 1024) {
        ext = " KB"
        mSize /= 1024
        if (mSize >= 1024) {
            ext = " MB"
            mSize /= 1024
            if (mSize >= 1024) {
                ext = " GB"
                mSize /= 1024
            }
        }
    }
    return if (ext != null) mSize.toString() + ext else mSize.toString()
}