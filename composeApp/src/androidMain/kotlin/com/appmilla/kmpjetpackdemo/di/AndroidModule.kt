package com.appmilla.kmpjetpackdemo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.repository.DefaultHelloWorldRepository
import data.repository.HelloWorldRepository
import data.repository.createDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import viewmodels.MainViewModel

val androidModule = module {
    single<HelloWorldRepository> { DefaultHelloWorldRepository() }
    single { dataStore(get()) }
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    viewModelOf(::MainViewModel)
}

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve("jetpacknativevm.preferences_pb").absolutePath }
    )