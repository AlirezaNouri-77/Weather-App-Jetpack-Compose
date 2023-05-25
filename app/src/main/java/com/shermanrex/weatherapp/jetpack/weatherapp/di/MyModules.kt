package com.shermanrex.weatherapp.jetpack.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


val Context.userDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "Weather")

@Module
@InstallIn(SingletonComponent::class)
class MyModules {

    @Provides
    @Singleton
    @Named("SearchApiRetrofit")
    fun retrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    @Named("OpenWeatherRetrofit")
    fun OpenWeatherRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    @Named("WeatherbitRetrofit")
    fun WeatherbitRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.weatherbit.io/v2.0/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Provides
    fun searchCityApiRepo(): SearchCityApiRepository {
        return SearchCityApiRepository(retrofit())
    }
    @Provides
    fun WeatherRepository(): WeatherRepository {
        return WeatherRepository(
            weatherbit_retrofit = WeatherbitRetrofit(),
            openweatherretrofit = OpenWeatherRetrofit()
        )
    }

    @Provides
    fun provideWeatherDataStore(@ApplicationContext context:Context):WeatherDataStore {
        return WeatherDataStore(provideDataStore(context))
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context:Context): DataStore<androidx.datastore.preferences.core.Preferences> {
        return context.userDataStore
    }

    companion object {

    }

}