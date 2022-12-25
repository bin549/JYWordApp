package com.android.wordapp.Dao

import androidx.room.*
import com.android.wordapp.model.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(userEntity: UserEntity)

    @Query("Select * from `user-table` where username=:username and password=:password")
    fun fetchUser(username: String, password: String): UserEntity
}