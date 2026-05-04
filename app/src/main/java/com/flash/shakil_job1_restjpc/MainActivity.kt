package com.flash.shakil_job1_restjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flash.shakil_job1_restjpc.ui.theme.Shakil_Job1_RestJPCTheme
import com.flash.shakil_job1_restjpc.ui.view.DetailScreen
import com.flash.shakil_job1_restjpc.ui.view.HomeScreen
import com.flash.shakil_job1_restjpc.ui.view.WelcomeScreen
import com.flash.shakil_job1_restjpc.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Shakil_Job1_RestJPCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "welcome"
                    ) {
                        composable("welcome") {
                            WelcomeScreen(navController)
                        }
                        composable("home") {
                            val viewModel: ProductViewModel = viewModel()
                            HomeScreen(navController, viewModel)
                        }
                        composable("detail/{productId}") { backStackEntry ->
                            val productId =
                                backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
                            DetailScreen(
                                productId = productId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
