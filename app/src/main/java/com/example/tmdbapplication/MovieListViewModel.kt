package com.example.tmdbapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdbapplication.data.Movie
import com.example.tmdbapplication.data.MovieListRepositary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(application: Application):AndroidViewModel(application)
{
    private val repo:MovieListRepositary = MovieListRepositary(application)
    val movies: LiveData<List<Movie>> = repo.getMovies()

    private val _loadingStatus = MutableLiveData<LoadingStatus>()!!


       val loadingStatus: LiveData<LoadingStatus>

          get() = _loadingStatus



    fun fetchFromNetwork(){
        _loadingStatus.value = LoadingStatus.loading()
        viewModelScope.launch {
            _loadingStatus.value =  withContext(Dispatchers.IO){
                delay(5000)
                repo.fetchfromNetwork()
            }
        }
    }
    fun refreshData(){
        viewModelScope.launch (Dispatchers.IO){
            repo.deleteAllData()
        }
        fetchFromNetwork()
    }
}