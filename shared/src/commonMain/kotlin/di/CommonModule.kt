package di

import data.repository.TimerRepository
import data.repository.TimerRepositoryImpl
import org.koin.dsl.module

val commonModule = module {
    single<TimerRepository> { TimerRepositoryImpl(get()) }
}