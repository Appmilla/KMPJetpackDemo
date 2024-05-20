package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

actual class KoinInitializer {
    actual fun init(platformSpecific: Module): KoinApplication = startKoin {
        modules(appModule() + platformSpecific)
    }
}