package di

import data.repositories.DefaultHelloWorldRepository
import data.repositories.HelloWorldRepository
import data.repositories.TimerRepository
import data.repositories.TimerRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodels.DetailViewModel
import viewmodels.HomeViewModel

val commonModule =
    module {
        single<TimerRepository> { TimerRepositoryImpl(get()) }
        viewModel { DetailViewModel(get(), get(), get()) }
        viewModel { HomeViewModel() }
        single<HelloWorldRepository> { DefaultHelloWorldRepository() }
        factory { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    }
