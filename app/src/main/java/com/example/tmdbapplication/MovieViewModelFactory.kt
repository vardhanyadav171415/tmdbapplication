package com.example.tmdbapplication

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MovieViewModelFactory(private val id:Long,private val context:Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MovieDetailViewModel(id,context) as T
    }
}