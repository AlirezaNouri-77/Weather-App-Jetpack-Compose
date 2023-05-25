package com.shermanrex.weatherapp.jetpack.weatherapp.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class WeatherDataStore @Inject constructor(
    var dataStore: DataStore<Preferences>
) {

    val flow: Flow<DataStoreModel> = dataStore.data.map {
        DataStoreModel(
            lat = it[LAT_KEY] ?: 0.0 ,
            lon = it[LON_KEY] ?: 0.0 ,
            unit = it[UNIT_KEY] ?: "metric"
            )
    }

    val unitFlow = dataStore.data.map {
            it[UNIT_KEY] ?: "metric"
    }

    suspend fun writeDataStore(lat: Double , lon: Double , unit:String) {
        dataStore.edit {
            if (lat == 0.0) {
                it[LAT_KEY] = it[LAT_KEY] ?: 0.0
                it[LON_KEY] = it[LON_KEY] ?: 0.0
                it[UNIT_KEY] = unit
            } else {
                it[LAT_KEY] = lat
                it[LON_KEY] = lon
                it[UNIT_KEY] = it[UNIT_KEY] ?: "metric"
            }
        }
    }

    companion object {
        var LAT_KEY = doublePreferencesKey("LAT_KEY")
        var LON_KEY = doublePreferencesKey("LON_KEY")
        var UNIT_KEY = stringPreferencesKey("units")
    }
}

data class DataStoreModel(
    var lat: Double ,
    var lon: Double ,
    var unit:String
)