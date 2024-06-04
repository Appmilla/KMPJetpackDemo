package com.appmilla.kmpjetpackdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import viewmodels.MainViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailView() {
    val mainViewModel = koinViewModel<MainViewModel>()
    val timer by mainViewModel.timer.collectAsState()

    MaterialTheme {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = timer.toString(),
            )
        }
    }
}

@Preview
@Composable
fun DetailViewPreview() {
    DetailView()
}

/*
@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailView(viewModelStoreOwnerDetailView: ViewModelStoreOwner) {
    val mainViewModel = koinViewModel<MainViewModel>()
    val timer by mainViewModel.timer.collectAsState()

    MaterialTheme {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = timer.toString(),
            )
        }
    }

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            viewModelStoreOwnerDetailView.viewModelStore.clear()
        }
    })
}

@Preview
@Composable
fun DetailViewPreview() {
    val viewModelStoreOwnerDetailView =
        object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = ViewModelStore()
        }
    DetailView(viewModelStoreOwnerDetailView)
}*/
