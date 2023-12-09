package com.example.cinemaview

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterUrl: String,
    val rating: Double
)