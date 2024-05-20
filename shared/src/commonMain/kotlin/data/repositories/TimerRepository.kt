package data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

fun createDataStore(
    producePath: () -> String,
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = emptyList(),
    produceFile = { producePath().toPath() },
)


interface TimerRepository {
    val timerValue: Flow<Int>
    suspend fun saveTimerValue(value: Int)
}

class TimerRepositoryImpl(private val dataStore: DataStore<Preferences>) : TimerRepository {
    private val timerKey = intPreferencesKey("timer_value")

    override suspend fun saveTimerValue(value: Int) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[timerKey] = value
            }
        }
    }

    override val timerValue: Flow<Int> = dataStore.data.map { preferences ->
        preferences[timerKey] ?: 0
    }
}
