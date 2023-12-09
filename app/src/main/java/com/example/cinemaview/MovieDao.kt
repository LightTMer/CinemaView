package com.example.cinemaview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemaview.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)
}
