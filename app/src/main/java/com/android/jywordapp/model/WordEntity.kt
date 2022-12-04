package com.android.jywordapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word-table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "",
    @ColumnInfo(name="explanation")
    val explanation: String = ""
)
