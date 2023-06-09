package com.shermanrex.weatherapp.jetpack.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.util.myDataStore
import com.shermanrex.weatherapp.jetpack.weatherapp.util.locationPermission
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


val Context.userDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "Weather"
)

@Module
@InstallIn(SingletonComponent::class)
class MyModules {

    @Provides
    @Singleton
    @Named("SearchApiRetrofit")
    fun retrofit(): RetrofitService {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    @Named("OpenWeatherRetrofit")
    fun OpenWeatherRetrofit(): RetrofitService {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).client(okhttpClient()).build()
            .create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    @Named("WeatherbitRetrofit")
    fun WeatherbitRetrofit(): RetrofitService {
        return Retrofit.Builder().baseUrl("https://api.weatherbit.io/v2.0/")
            .addConverterFactory(GsonConverterFactory.create()).client(okhttpClient()).build()
            .create(RetrofitService::class.java)
    }

    @Provides
    fun searchCityApiRepo(): SearchCityApiRepository {
        return SearchCityApiRepository(retrofit())
    }

    @Provides
    fun locationPermission(@ApplicationContext context: Context): locationPermission {
        return com.shermanrex.weatherapp.jetpack.weatherapp.util.locationPermission(context)
    }


    @Provides
    fun WeatherRepository(): WeatherRepository {
        return WeatherRepository(
            weatherbitretrofit = WeatherbitRetrofit(),
            openweatherretrofit = OpenWeatherRetrofit()
        )
    }

    @Provides
    fun provideWeatherDataStore(@ApplicationContext context: Context): myDataStore {
        return myDataStore(provideDataStore(context))
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<androidx.datastore.preferences.core.Preferences> {
        return context.userDataStore
    }

    private fun okhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .callTimeout(60,  TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        return logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}