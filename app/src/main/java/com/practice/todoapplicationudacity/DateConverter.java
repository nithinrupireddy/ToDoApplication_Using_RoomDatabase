package com.practice.todoapplicationudacity;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestramp){
        return timestramp == null ? null :new Date(timestramp);
    }

    @TypeConverter
    public static Long toTimeStramp(Date date) {
        return date == null ? null : date.getTime();
    }
}
