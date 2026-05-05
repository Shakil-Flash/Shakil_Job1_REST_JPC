package com.flash.shakil_job1_restjpc.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.flash.shakil_job1_restjpc.ui.theme.Primary
import kotlinx.coroutines.delay

// Ironman Colors
private val IronRed = Color(0xFFD32F2F)
private val IronRedDark = Color(0xFFB71C1C)
private val IronGold = Color(0xFFFFD700)
private val ReactorBlue = Color(0xFF00E5FF)
private val ReactorGlow = Color(0xFF18FFFF)

@Composable
fun WelcomeScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(IronRedDark, IronRed)
                )
            )
    ) {
        // Animated background particles/glow effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawCircle(
                        color = ReactorBlue.copy(alpha = 0.1f),
                        radius = size.maxDimension * 0.4f,
                        center = Offset(size.width * 0.5f, size.height * 0.35f)
                    )
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Arc Reactor
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -100 })
            ) {
                ArcReactor()
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Animated Title
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 1000, delayMillis = 300)
                ) + slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1000, delayMillis = 300)
                )
            ) {
                Text(
                    text = "WELCOME TO SHOP",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animated Subtitle
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 1000, delayMillis = 600)
                ) + slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1000, delayMillis = 600)
                )
            ) {
                Text(
                    text = "Discover amazing products with power of technology",
                    fontSize = 16.sp,
                    color = IronGold.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(56.dp))

            // Animated Button
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 1000, delayMillis = 900)
                ) + slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1000, delayMillis = 900)
                )
            ) {
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IronGold,
                        contentColor = IronRedDark
                    )
                ) {
                    Text(
                        text = "ACTIVATE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow Forward",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Animated Footer Text
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 1000, delayMillis = 1200)
                )
            ) {
                Text(
                    text = "Powered by J.A.R.V.I.S. Technology",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun ArcReactor() {
    var isPulsing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1500)
            isPulsing = !isPulsing
        }
    }

    val pulseScale by animateFloatAsState(
        targetValue = if (isPulsing) 1.2f else 0.8f,
        animationSpec = tween(durationMillis = 750),
        label = "pulse"
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (isPulsing) 0.8f else 0.3f,
        animationSpec = tween(durationMillis = 750),
        label = "glow"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp)
    ) {
        // Outer glow
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(ReactorBlue.copy(alpha = glowAlpha * 0.3f))
        )

        // Middle ring
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(ReactorGlow.copy(alpha = 0.5f), Color.Transparent)
                    )
                )
        )

        // Inner circle - the core
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White, ReactorBlue)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Icon in center
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Reactor Core",
                modifier = Modifier.size(32.dp),
                tint = IronRedDark
            )
        }

        // Rotating outer ring effect
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .drawBehind {
                    drawCircle(
                        color = ReactorBlue.copy(alpha = 0.3f),
                        radius = size.minDimension / 2,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                    )
                }
        )
    }
}
