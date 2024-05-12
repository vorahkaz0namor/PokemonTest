package com.ccink.model.di

import com.ccink.model.Constants.Companion.BACKEND_API_URL
import com.ccink.model.network.PokemonApi
import com.ccink.model.network.PokemonApiService
import com.ccink.model.network.PokemonApiServiceImpl
import com.ccink.model.repository.PokemonRepository
import com.ccink.model.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .callTimeout(60000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonApi(
        okHttpClient: OkHttpClient
    ): PokemonApi = Retrofit.Builder()
        .baseUrl(BACKEND_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client((okHttpClient))
        .build()
        .create()

    @Singleton
    @Provides
    fun providePokemonApiServiceImpl(
        api: PokemonApi
    ): PokemonApiService = PokemonApiServiceImpl(api)

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokemonApiService: PokemonApiService
    ): PokemonRepository = PokemonRepositoryImpl(pokemonApiService)
}