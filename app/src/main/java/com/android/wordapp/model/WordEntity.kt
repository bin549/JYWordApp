package com.android.wordapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word-table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "",
    @ColumnInfo(name="explanation")
    val explanation: String = "",
    @ColumnInfo(name="user_id")
    val userId: Int = 0,
    @ColumnInfo(name="is_known")
    val isKnown: Int = 0,
)
