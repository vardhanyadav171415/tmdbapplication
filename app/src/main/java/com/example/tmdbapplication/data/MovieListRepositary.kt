package com.example.tmdbapplication.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.tmdbapplication.ErrorCode
import com.example.tmdbapplication.LoadingStatus
import com.example.tmdbapplication.network.TmdbServie
import java.net.UnknownHostException

class MovieListRepositary(context:Application) {
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService by lazy { TmdbServie.getInstance() }
    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    suspend fun fetchfromNetwork() =
        try {
            val result = tmdbService.getMovies()
            if (result.isSuccessful) {
                val moviesList = result.body()
                moviesList?.let { movieListDao.insertMovie(it.results) }
                LoadingStatus.sucess()
            } else {
                  LoadingStatus.error(ErrorCode.NO_DATA)
            }
        }catch (ex:UnknownHostException){
            LoadingStatus.error(ErrorCode.NETWORK_ERROR)
        }
    catch (ex:Exception){
        LoadingStatus.error(ErrorCode.UNKNOWN_ERROR,ex.message)
    }

    suspend fun deleteAllData(){
        movieListDao.deleteAllData()
    }
}