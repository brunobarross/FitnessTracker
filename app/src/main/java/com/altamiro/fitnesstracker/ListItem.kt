package com.altamiro.fitnesstracker

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.Date

data class ListItem(
    val id: Int,
    val type: String,
    val res: Double,
    val createdAt: Date
)
