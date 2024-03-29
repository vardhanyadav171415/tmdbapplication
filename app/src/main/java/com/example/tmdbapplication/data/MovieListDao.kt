package com.example.tmdbapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieListDao {

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    fun getMovies():LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movies:List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun deleteAllData()
}