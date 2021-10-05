package com.example.bookstore.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookstore.db.entities.Favorites
import com.google.android.material.circularreveal.CircularRevealHelper
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoritesDao {

    @Query("Select * from Favorites")
    fun getAllFavoriteBooks():Single<List<Favorites>>

    @Query("Select * from Favorites where book_id = :bookId")
    fun getFavoriteBookById(bookId:String):Single<List<Favorites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteBook(favorites: Favorites):Completable

    @Query("Delete from Favorites where book_id = :bookId")
    fun deleteFavoriteBook(bookId: String):Completable
}