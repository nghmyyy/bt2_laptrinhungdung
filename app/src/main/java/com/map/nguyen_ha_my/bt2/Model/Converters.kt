package com.map.nguyen_ha_my.bt2.Model

import androidx.room.TypeConverter
import java.util.Date

public class Converters {  // Đảm bảo có 'public' ở đây
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
