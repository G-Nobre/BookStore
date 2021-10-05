package com.example.bookstore.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class Favorites(
    @PrimaryKey @ColumnInfo(name = "book_id") var book_id: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean = true,
)
