package com.android.jywordapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.jywordapp.Dao.UserDao
import com.android.jywordapp.Dao.WordDao
import com.android.jywordapp.model.UserEntity
import com.android.jywordapp.model.WordEntity

@Database(entities = [WordEntity::class, UserEntity::class], version = 13)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}