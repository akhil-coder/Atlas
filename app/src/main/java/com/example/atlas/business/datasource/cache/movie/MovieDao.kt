package com.example.atlas.business.datasource.cache.movie

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_list ORDER BY popularity DESC")
    suspend fun getAllMovies(): List<MovieEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity): Long

    @Query("DELETE FROM movie_list")
    suspend fun deleteAllMovies()

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movie_list WHERE id = :id")
    suspend fun deleteMovie(id: Int)

    @Query("SELECT * FROM movie_list WHERE id = :id")
    suspend fun getMovie(id: Int): MovieEntity?

}