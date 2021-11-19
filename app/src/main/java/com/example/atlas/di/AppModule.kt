package com.example.atlas.di

import android.app.Application
import androidx.room.Room
import com.example.atlas.business.datasource.cache.AppDatabase
import com.example.atlas.business.datasource.cache.AppDatabase.Companion.DATABASE_NAME
import com.example.atlas.business.datasource.cache.movie.MovieDao
import com.example.atlas.business.datasource.network.main.TMDBService
import com.example.atlas.business.domain.utils.Constants
import com.example.atlas.business.interactors.movie.DiscoverMovie
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao {
        return db.getMovieDao()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder:  Gson, httpClient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(httpClient)
    }

    @Singleton
    @Provides
    fun provideTMDBService(retrofitBuilder: Retrofit.Builder): TMDBService {
        return retrofitBuilder
            .build()
            .create(TMDBService::class.java)
    }

    @Singleton
    @Provides
    fun provideDiscoverMovie(service: TMDBService, cache: MovieDao): DiscoverMovie {
        return DiscoverMovie(service, cache)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}