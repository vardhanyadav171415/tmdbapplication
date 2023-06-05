package com.example.tmdbapplication

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbapplication.data.Movie
import com.example.tmdbapplication.data.MovieDetailRepositary

class MovieDetailViewModel(id:Long,application: Application):ViewModel() {
    private val repo:MovieDetailRepositary= MovieDetailRepositary(application)
    val movie:LiveData<Movie> =
        repo.getMovie(id)
}