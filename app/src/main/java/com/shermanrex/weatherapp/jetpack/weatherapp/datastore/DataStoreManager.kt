package com.shermanrex.weatherapp.jetpack.weatherapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataStoreManager @Inject constructor(
  private var dataStore: DataStore<Preferences>
) {
  
  suspend fun getCoordinator(): DataStoreModel {
	return dataStore.data.map {
	  DataStoreModel(
		lat = it[LAT_KEY] ?: 0.0,
		lon = it[LON_KEY] ?: 0.0,
		unit = it[UNIT_KEY] ?: "METRIC"
	  )
	}.first()
  }
  
  val getUnitDataStore: String
	get() = runBlocking {
	  dataStore.data.map {
		it[UNIT_KEY] ?: "METRIC"
	  }.first()
	}
  
  fun writeUnitDataStore(unit: String) {
	runBlocking {
	  dataStore.edit {
		it[UNIT_KEY] = unit
	  }
	}
  }
  
  fun writeCoordinatorDataStore(lat: Double, lon: Double) {
	runBlocking {
	  dataStore.edit {
		it[LAT_KEY] = lat
		it[LON_KEY] = lon
	  }
	}
  }
  
  val checkDataStoreIsEmpty: Boolean
	get() = runBlocking {
	  dataStore.data.map {
		it.contains(LAT_KEY)
	  }.first()
	}
  
  companion object {
	var LAT_KEY = doublePreferencesKey("LAT_KEY")
	var LON_KEY = doublePreferencesKey("LON_KEY")
	var UNIT_KEY = stringPreferencesKey("units")
  }
  
}
