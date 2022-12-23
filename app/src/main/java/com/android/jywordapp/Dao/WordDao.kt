package com.android.jywordapp.Dao

import androidx.room.*
import com.android.jywordapp.model.WordEntity
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
    fun fetchALlWords(userId: Int): Flow<List<WordEntity>>

    @Query("Select * from `word-table` where user_id=:userId and is_kown=:isKnown")
    fun fetchWordsByIsKnown(userId: Int, isKnown: Int): Flow<List<WordEntity>>

    @Query("Select * from `word-table` where id=:id")
    fun fetchALlWordById(id: Int): Flow<WordEntity>

    @Query("Select count(*) from `word-table` where user_id=:userId and is_kown=1")
    fun countKnownWordById(userId: Int): Flow<Int>
}
