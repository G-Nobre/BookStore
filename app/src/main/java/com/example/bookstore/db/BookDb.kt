package com.example.bookstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookstore.db.daos.FavoritesDao
import com.example.bookstore.db.daos.RemoteKeyDao
import com.example.bookstore.db.entities.Favorites
import com.example.bookstore.db.entities.RemoteKey
import io.reactivex.Single
import io.reactivex.SingleObserver


@Database(
    entities = [Favorites::class,RemoteKey::class],
    version = 2,
    exportSchema = false
)

//@TypeConverters(Converters::class)
abstract class BookDb : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun remoteKeysDao(): RemoteKeyDao

    companion object {
        private var INSTANCE: BookDb? = null

        fun getInstance(context: Context): BookDb? {
            if (INSTANCE == null) {
                synchronized(BookDb::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            BookDb::class.java, "BookDb"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
