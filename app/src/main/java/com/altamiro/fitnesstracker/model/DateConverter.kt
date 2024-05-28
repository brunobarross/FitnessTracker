package com.altamiro.fitnesstracker.model

import androidx.room.TypeConverter
import java.util.Date

object DateConverter {
    //usado na buscado objeto
    @TypeConverter
    fun toDate(dateLong: Long?) : Date?{
        return if(dateLong != null) Date(dateLong) else null

    }

    //usado pra gravar, converte a data atual em timestamp
    @TypeConverter
    fun fromDate(date: Date?) : Long?{
        return date?.time
    }
}