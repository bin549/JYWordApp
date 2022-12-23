package com.android.jywordapp.Dao

import androidx.room.*
import com.android.jywordapp.model.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(userEntity: UserEntity)

    @Query("Select * from `user-table` where username=:username and password=:password")
    fun fetchUser(username: String, password: String): UserEntity
}