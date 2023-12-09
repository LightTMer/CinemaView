package com.example.cinemaview

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemaview.MovieDao
import com.example.cinemaview.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
