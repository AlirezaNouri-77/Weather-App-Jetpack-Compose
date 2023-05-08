package com.shermanrex.weatherapp.jetpack.weatherapp.di

import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class MyModules {

    @Provides
    @Named("SearchApiRetrofit")
    fun retrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Named("3hourApiRetrofit")
    fun threehourRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Named("WeatherbitRetrofit")
    fun Currentretrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.weatherbit.io/v2.0/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


    @Provides
    fun searchCityApiRepo(): SearchCityApiRepository {
        return SearchCityApiRepository(retrofit())
    }

    @Provides
    fun WeatherRepository(): WeatherRepository {
        return WeatherRepository(Currentretrofit() , threehourRetrofit())
    }

}