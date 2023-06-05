package com.example.tmdbapplication

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

class DbTypeConverters {

    @TypeConverter
    fun fromTimeStamp(value:Long): Date?{
        return if (value==null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date:Date?):Long?{
        return date?.time
    }
}