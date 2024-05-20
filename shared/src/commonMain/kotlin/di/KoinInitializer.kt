package di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

expect class KoinInitializer {
    fun init(platformSpecific: Module = module { }): KoinApplication
}