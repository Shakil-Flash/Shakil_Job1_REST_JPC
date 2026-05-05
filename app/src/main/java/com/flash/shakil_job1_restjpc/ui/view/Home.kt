package com.flash.shakil_job1_restjpc.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.flash.shakil_job1_restjpc.data.model.Product
import com.flash.shakil_job1_restjpc.ui.component.ProductCard
import com.flash.shakil_job1_restjpc.utils.Resource
import com.flash.shakil_job1_restjpc.viewmodel.ProductViewModel
import kotlinx.coroutines.delay

// Ironman Colors
private val IronRed = Color(0xFFD32F2F)
private val IronRedDark = Color(0xFFB71C1C)
private val IronGold = Color(0xFFFFD700)
private val ReactorBlue = Color(0xFF00E5FF)

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val productsState by viewModel.products.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    // Animation visibility states
    var titleVisible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        titleVisible = true
        delay(300)
        contentVisible = true
        viewModel.loadProducts()
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SHOP",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = IronRed,
                        letterSpacing = 2.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = { navController.navigate("search") },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(Icons.Default.Info, contentDescription = "Search", tint = IronRed)
                        }
                        IconButton(
                            onClick = { viewModel.shuffleProducts() },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Shuffle", tint = IronRed)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Animated Title
                AnimatedVisibility(
                    visible = titleVisible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { -it })
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "PRODUCTS",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = IronRedDark,
                            letterSpacing = 1.sp
                        )
                        // Mini reactor indicator
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Color.White, ReactorBlue)
                                    )
                                )
                        )
                    }
                }

                // Content area with animation
                AnimatedVisibility(
                    visible = contentVisible,
                    modifier = Modifier.weight(1f),
                    enter = fadeIn(
                        animationSpec = androidx.compose.animation.core.tween(durationMillis = 600)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (productsState) {
                            is Resource.Loading -> {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(48.dp),
                                        color = IronRed
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Loading products...",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            is Resource.Success -> {
                                val products = (productsState as Resource.Success<List<Product>>).data
                                if (products.isNullOrEmpty()) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "No products found",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                } else {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2),
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = PaddingValues(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        items(products) { product ->
                                            ProductCard(
                                                product = product,
                                                onClick = {
                                                    navController.navigate("detail/${product.id}")
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            is Resource.Error -> {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .size(64.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.errorContainer
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "!",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Oops! Something went wrong",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = (productsState as Resource.Error).message ?: "Error",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    Button(
                                        onClick = { viewModel.loadProducts() },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("Try Again")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
