package com.example.bookstore.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookstore.db.entities.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(keys: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE bookId = :bookId")
    fun remoteKeyById(bookId: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE bookId = :bookId")
    fun deleteByBookId(bookId: String)
}