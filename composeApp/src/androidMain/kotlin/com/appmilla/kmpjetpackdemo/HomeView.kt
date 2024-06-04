package com.appmilla.kmpjetpackdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import viewmodels.HomeViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeView(navController: NavController) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    val message by homeViewModel.message.collectAsState()

    MaterialTheme {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Text(message, Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navController.navigate("detail")
                }) {
                    Text("Show DetailView")
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    HomeView(navController)
}
