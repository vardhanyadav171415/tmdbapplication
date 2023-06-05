package com.example.tmdbapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbapplication.data.Movie
import com.example.tmdbapplication.network.TmdbServie
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer


class MovieAdapter(private val listener:(Long)->Unit) :  androidx.recyclerview.widget.ListAdapter<Movie,MovieAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var movietitle: TextView
    private lateinit var movie_poster:ImageView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemLayout=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer{

        init {
            movie_poster=itemView.findViewById(R.id.movie_imageview)
             movietitle=itemView.findViewById(R.id.movie)
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).id)
            }
        }
       fun bind(movie:Movie){
           with(movie){

               Picasso.get()
                   .load(TmdbServie.POSTER_BASE_URL + movie.posterPath)
                   .error(R.drawable.download)
                   .into(movie_poster);
               movietitle.text=movie.title
           }
       }
    }
    class DiffCallback:DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }

    }





}
