package com.flash.shakil_job1_restjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flash.shakil_job1_restjpc.ui.component.BottomNavigationBar
import com.flash.shakil_job1_restjpc.ui.theme.Shakil_Job1_RestJPCTheme
import com.flash.shakil_job1_restjpc.ui.view.CartScreen
import com.flash.shakil_job1_restjpc.ui.view.DetailScreen
import com.flash.shakil_job1_restjpc.ui.view.HomeScreen
import com.flash.shakil_job1_restjpc.ui.view.ProfileScreen
import com.flash.shakil_job1_restjpc.ui.view.SearchScreen
import com.flash.shakil_job1_restjpc.ui.view.WelcomeScreen
import com.flash.shakil_job1_restjpc.viewmodel.CartViewModel
import com.flash.shakil_job1_restjpc.viewmodel.ProfileViewModel
import com.flash.shakil_job1_restjpc.viewmodel.ProfileViewModelFactory
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
                    val viewModel: ProductViewModel = viewModel()
                    val cartViewModel: CartViewModel = remember { CartViewModel(this@MainActivity) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val cartItemCount by cartViewModel.cartItems.collectAsState()

                    val showBottomNav = currentRoute !in listOf("welcome", "detail/{productId}")

                    Scaffold(
                        bottomBar = {
                            if (showBottomNav) {
                                BottomNavigationBar(
                                    currentRoute = currentRoute ?: "home",
                                    onNavigate = { route ->
                                        navController.navigate(route) {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    cartItemCount = cartItemCount.sumOf { it.quantity }
                                )
                            }
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = "welcome",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            composable("welcome") {
                                WelcomeScreen(navController)
                            }
                            composable("home") {
                                HomeScreen(navController, viewModel)
                            }
                            composable("search") {
                                SearchScreen(navController, viewModel)
                            }
                            composable("cart") {
                                CartScreen(navController, cartViewModel)
                            }
                            composable("profile") {
                                val profileViewModel: ProfileViewModel = viewModel(
                                    factory = ProfileViewModelFactory(this@MainActivity)
                                )
                                ProfileScreen(navController, profileViewModel)
                            }
                            composable("detail/{productId}") { backStackEntry ->
                                val productId =
                                    backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
                                DetailScreen(
                                    productId = productId,
                                    navController = navController,
                                    viewModel = viewModel,
                                    cartViewModel = cartViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


//Wow i made a nice app

