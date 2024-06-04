package com.appmilla.kmpjetpackdemo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.repositories.createDataStore
import org.koin.dsl.module

val androidModule =
    module {
        single { dataStore(get()) }
    }

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve("jetpacknativevm.preferences_pb").absolutePath },
    )
