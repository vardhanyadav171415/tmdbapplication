package com.example.tmdbapplication.data

import android.app.Application
import androidx.lifecycle.LiveData

class MovieDetailRepositary(context:Application) {
    private val movieDetailDao:MovieDetailDao = MovieDatabase.getDatabase(context).movieDetailDao()

    fun getMovie(id:Long):LiveData<Movie>{
        return movieDetailDao.getMovie(id)
    }
}