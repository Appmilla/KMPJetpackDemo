package com.appmilla.kmpjetpackdemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

// From this Gist
// https://gist.github.com/manuelvicnt/a2e4c4812243ac1b218b24d0ac8d22bb

internal class CompositionScopedViewModelStoreOwner : ViewModelStoreOwner, RememberObserver {
    override val viewModelStore = ViewModelStore()

    override fun onAbandoned() {
        viewModelStore.clear()
    }

    override fun onForgotten() {
        viewModelStore.clear()
    }

    override fun onRemembered() {
        // Nothing to do here
    }
}

@Composable
fun ProvidesViewModelStoreOwner(content: @Composable () -> Unit) {
    val viewModelStoreOwner = remember { CompositionScopedViewModelStoreOwner() }
    CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
        content()
    }
}
