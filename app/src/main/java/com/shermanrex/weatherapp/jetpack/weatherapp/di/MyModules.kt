package com.shermanrex.weatherapp.jetpack.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.datastore.DataStoreManager
import com.shermanrex.weatherapp.jetpack.weatherapp.util.NetworkConnectivity
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


val Context.dataStoreManager: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
  name = "Weather"
)

@Module
@InstallIn(SingletonComponent::class)
object MyModules {
  
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
  @Singleton
  fun searchCityApiRepo(@ApplicationContext context: Context): SearchCityRepository {
	return SearchCityRepository(
	  retrofit = retrofit(),
	  networkConnectivity = networkConnectivity(context),
	)
  }
  
  @Provides
  @Singleton
  fun WeatherRepository(@ApplicationContext context: Context): WeatherRepository {
	return WeatherRepository(
	  weatherbitretrofit = WeatherbitRetrofit(),
	  openweatherretrofit = OpenWeatherRetrofit(),
	  dataStoreManager = provideDataStoreManager(context),
	)
  }
  
  @Provides
  @Singleton
  fun networkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
	return NetworkConnectivity(context = context)
  }
  
  @Provides
  @Singleton
  fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
	return DataStoreManager(context.dataStoreManager)
  }
  
//  @Provides
//  @Singleton
//  fun provideDataStore(@ApplicationContext context: Context): DataStore<androidx.datastore.preferences.core.Preferences> {
//	return context.userDataStore
//  }
  
  private fun okhttpClient(): OkHttpClient {
	return OkHttpClient.Builder()
	  .callTimeout(30, TimeUnit.SECONDS)
	  .connectTimeout(30, TimeUnit.SECONDS)
	  .readTimeout(30, TimeUnit.SECONDS)
	  .addInterceptor(loggingInterceptor())
	  .build()
  }
  
  private fun loggingInterceptor(): HttpLoggingInterceptor {
	val logging = HttpLoggingInterceptor()
	return logging.setLevel(HttpLoggingInterceptor.Level.BODY)
  }
  
}