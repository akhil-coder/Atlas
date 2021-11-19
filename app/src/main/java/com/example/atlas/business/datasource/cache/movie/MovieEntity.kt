package com.example.atlas.business.datasource.cache.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.atlas.business.domain.models.Movie

@Entity(tableName = "movie_list")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double
)

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id =  id,
        title =  title,
        posterPath =  posterPath,
        voteAverage =  voteAverage,
        overview =  overview,
        releaseDate =  releaseDate,
        popularity = popularity
    )
}
