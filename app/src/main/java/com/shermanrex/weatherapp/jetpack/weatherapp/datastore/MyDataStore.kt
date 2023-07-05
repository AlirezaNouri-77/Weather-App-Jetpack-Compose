package com.shermanrex.weatherapp.jetpack.weatherapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shermanrex.weatherapp.jetpack.weatherapp.models.DataStoreModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class MyDataStore @Inject constructor(
    private var dataStore: DataStore<Preferences>
) {

    fun dataStoreFlow(): Flow<DataStoreModel> {
        return dataStore.data.map {
            runBlocking {
                DataStoreModel(
                    lat = it[LAT_KEY] ?: 0.0,
                    lon = it[LON_KEY] ?: 0.0,
                    unit = it[UNIT_KEY] ?: "METRIC"
                )
            }
        }
    }

    val getUnitDataStore: String
        get() = runBlocking {
            dataStore.data.map {
                it[UNIT_KEY] ?: "METRIC"
            }.first()
        }

    suspend fun writeUnitDataStore(unit: String) {
        runBlocking {
            dataStore.edit {
                it[UNIT_KEY] = unit
            }
        }
    }

    suspend fun writeCoordinatorDataStore(lat: Double, lon: Double) {
        runBlocking {
            dataStore.edit {
                it[LAT_KEY] = lat
                it[LON_KEY] = lon
            }
        }
    }

    companion object {
        var LAT_KEY = doublePreferencesKey("LAT_KEY")
        var LON_KEY = doublePreferencesKey("LON_KEY")
        var UNIT_KEY = stringPreferencesKey("units")
    }

}
