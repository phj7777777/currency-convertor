package com.example.convertor.helper
import java.util.*

fun getDateTime(ts:String?):String{

   val convertedTs = ts?.toLongOrNull() ?: return ""
    //Get instance of calendar
    val calendar = Calendar.getInstance(Locale.getDefault())
    //get current date from convertedTs
    calendar.timeInMillis = (convertedTs -1 )* 1000
    //return formatted date
    return android.text.format.DateFormat.format("yyyy-MM-dd' 'HH:mm:ss", calendar).toString()
}