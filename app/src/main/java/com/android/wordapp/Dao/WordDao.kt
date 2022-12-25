package com.android.wordapp.Dao

import androidx.room.*
import com.android.wordapp.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert
    fun insert(wordEntity: WordEntity)

    @Update
    fun update(wordEntity: WordEntity)

    @Delete
    fun delete(wordEntity: WordEntity)

    @Query("Select * from `word-table` where user_id=:userId")
    fun fetchAllWords(userId: Int): Flow<List<WordEntity>>

    @Query("Select * from `word-table` where user_id=:userId and is_known=:isKnown order by id desc")
    fun fetchWordsByIsKnown(userId: Int, isKnown: Int): Flow<List<WordEntity>>

    @Query("Select * from `word-table` where id=:id")
    fun fetchAllWordById(id: Int): Flow<WordEntity>

    @Query("Select count(*) from `word-table` where user_id=:userId and is_known=1")
    fun countKnownWordById(userId: Int): Flow<Int>
}
