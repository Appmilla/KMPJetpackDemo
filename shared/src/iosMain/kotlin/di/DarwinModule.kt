package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.repository.DefaultHelloWorldRepository
import data.repository.HelloWorldRepository
import data.repository.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import viewmodels.MainViewModel

val darwinModule = module {
    single<HelloWorldRepository> { DefaultHelloWorldRepository() }
    single { dataStore()}
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    single { MainViewModel(get(), get(), get() ) }
}

@OptIn(ExperimentalForeignApi::class)
fun dataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/jetpacknativevm.preferences_pb"
    }
)