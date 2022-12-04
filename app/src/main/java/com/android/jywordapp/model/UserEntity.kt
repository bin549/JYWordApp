package com.android.jywordapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user-table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="username")
    val username: String = "",
    @ColumnInfo(name="password")
    val password: String = ""
)
