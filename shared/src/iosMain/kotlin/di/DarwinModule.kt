package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.repositories.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

val darwinModule =
    module {
        single { dataStore() }
    }

@OptIn(ExperimentalForeignApi::class)
fun dataStore(): DataStore<Preferences> =
    createDataStore(
        producePath = {
            val documentDirectory: NSURL? =
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
            requireNotNull(documentDirectory).path + "/jetpacknativevm.preferences_pb"
        },
    )
