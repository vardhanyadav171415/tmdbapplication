package com.example.tmdbapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName="movie")
data class Movie(@PrimaryKey val id:Long,
          val title:String,

                 @SerializedName("poster_path")
           @ColumnInfo(name="poster_path")
           val posterPath:String,

                 @SerializedName("backdrop_path")
          @ColumnInfo(name="backdrop_path")
          val backdropPath:String,
           val overview:String,

                 @SerializedName("release_date")
          @ColumnInfo(name="release_date")
          val releaseDate: Date
)