package com.appmilla.kmpjetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                KoinContext {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeView(navController) }
                        composable("detail") {
                            DetailView()
                            DisposableEffect(Unit) {
                                onDispose {
                                    // Clear the ViewModelStore when navigating away from the DetailView
                                    ViewModelStore().clear()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
