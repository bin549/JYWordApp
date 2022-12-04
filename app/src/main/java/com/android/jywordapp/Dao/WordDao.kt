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

    @Query("Select * from `word-table`")
    fun fetchALlWords(): Flow<List<WordEntity>>

    @Query("Select * from `word-table` where id=:id")
    fun fetchALlWordById(id: Int): Flow<WordEntity>
}