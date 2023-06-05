package com.example.tmdbapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tmdbapplication.databinding.FragmentMovieListBinding


class MovieListFragment : Fragment() {
    private lateinit var movielist:RecyclerView
    private lateinit var loading_status:ProgressBar
    private lateinit var status_error:TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var binding:FragmentMovieListBinding
    private lateinit var viewModel: MovieListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMovieListBinding.inflate(layoutInflater)
        movielist=binding.movieList
        loading_status=binding.loadingStatus
        status_error=binding.textView
        swipeRefreshLayout=binding.swipeRefresh
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(MovieListViewModel::class.java)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(movielist){
            adapter=MovieAdapter{
                findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it))
            }
        }
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            (movielist.adapter as MovieAdapter).submitList(it)
           if(it.isEmpty()){
               viewModel.fetchFromNetwork()
           }
        })

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer {loadingStatus ->
            when {
                loadingStatus?.status == Status.LOADING -> {
                    loading_status.visibility = View.VISIBLE
                    status_error.visibility = View.INVISIBLE
                }
                loadingStatus?.status == Status.SUCCESS -> {
                    loading_status.visibility = View.INVISIBLE
                    status_error.visibility = View.INVISIBLE
                }
                loadingStatus?.status == Status.ERROR -> {
                    loading_status.visibility = View.INVISIBLE
                    showErrorMessage(loadingStatus.errorCode, loadingStatus.message)
                    status_error.visibility = View.VISIBLE
                }
            }
            swipeRefreshLayout.isRefreshing=false
        })
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> status_error.text = "There is no data presenet"
            ErrorCode.NETWORK_ERROR -> status_error.text ="It has been caused due to network error"
            ErrorCode.UNKNOWN_ERROR -> status_error.text = "There is an unknown error causing this "
            else -> {"Unknown error"}
        }
    }
}