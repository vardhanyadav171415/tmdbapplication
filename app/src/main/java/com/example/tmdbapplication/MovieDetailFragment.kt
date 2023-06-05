package com.example.tmdbapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tmdbapplication.data.Movie
import com.example.tmdbapplication.data.readableFormat
import com.example.tmdbapplication.databinding.FragmentMovieDetailBinding
import com.example.tmdbapplication.network.TmdbServie
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


class MovieDetailFragment : Fragment() {
    private lateinit var binding:FragmentMovieDetailBinding
    private lateinit var movietitle:TextView
    private lateinit var movieoverview:TextView
    private lateinit var moviereleasedate:TextView
    private lateinit var poster:ImageView
    private lateinit var backdrop:ImageView

   private lateinit var viewmodel: MovieDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMovieDetailBinding.inflate(layoutInflater)
        movietitle=binding.movie
        movieoverview=binding.movieOverview
        moviereleasedate=binding.movieReleaseDate
        poster=binding.moviePoster
        backdrop=binding.movieBackdrop
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id:Long=MovieDetailFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = MovieViewModelFactory(id,requireActivity().application)
        viewmodel=ViewModelProviders.of(this,viewModelFactory).get(MovieDetailViewModel::class.java)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel.movie.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    private fun setData(it: Movie) {
        Picasso.get()
            .load(TmdbServie.POSTER_BASE_URL + it.posterPath)
            .error(R.drawable.download)
            .into(poster);
        Picasso.get()
            .load(TmdbServie.BACKDROP_BASE_URL + it.backdropPath)
            .error(R.drawable.download)
            .into(backdrop);
         movietitle.text=it.title
        movieoverview.text=it.overview
        moviereleasedate.text= it.releaseDate.readableFormat()

    }

}