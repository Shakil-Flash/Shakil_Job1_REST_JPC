package com.flash.shakil_job1_restjpc.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount: Int = 0
)

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    cartItemCount: Int = 0
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Search", Icons.Default.Search, "search"),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, "cart", cartItemCount),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (item.badgeCount > 0) {
                        BadgedBox(badge = { Badge { Text(item.badgeCount.toString(), fontSize = 10.sp) } }) {
                            Icon(item.icon, contentDescription = item.label)
                        }
                    } else {
                        Icon(item.icon, contentDescription = item.label)
                    }
                },
                label = { Text(item.label, fontSize = 11.sp) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }
}
