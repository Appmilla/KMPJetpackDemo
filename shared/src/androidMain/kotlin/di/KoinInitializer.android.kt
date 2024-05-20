package di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

actual class KoinInitializer(
    private val context: Context
) {
    actual fun init(platformSpecific: Module): KoinApplication = startKoin {
        androidContext(context)
        androidLogger()
        modules(appModule() + platformSpecific)
    }
}