package com.example.dailynews

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.*

//class timeVIew {

    @RequiresApi(Build.VERSION_CODES.N)
    val isoDateFormat= SimpleDateFormat("YYYY-MM-DD'T'HH:ss:SSS'Z'", Locale.getDefault())

    @RequiresApi(Build.VERSION_CODES.N)
    val appDateFormat=SimpleDateFormat("MMMM DD,YYYY",Locale.getDefault())
    @RequiresApi(Build.VERSION_CODES.N)
    fun TextView.timestamp(Time:String) {
        val date = isoDateFormat.parse(Time)
        text= appDateFormat.format(date)

    }


//}
//val date = isoDateFormat.parse(timestamp)
//text= appDateFormat.format(date)