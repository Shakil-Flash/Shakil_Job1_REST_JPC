package com.flash.shakil_job1_restjpc.ui.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.flash.shakil_job1_restjpc.ui.theme.Primary
import com.flash.shakil_job1_restjpc.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay

// Ironman Colors
private val IronRed = Color(0xFFD32F2F)
private val IronRedDark = Color(0xFFB71C1C)
private val IronGold = Color(0xFFFFD700)
private val ReactorBlue = Color(0xFF00E5FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    var isEditing by remember { mutableStateOf(false) }

    // Animation states
    var titleVisible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    // Observe ViewModel state
    val name by profileViewModel.name.collectAsState()
    val email by profileViewModel.email.collectAsState()
    val phone by profileViewModel.phone.collectAsState()
    val bio by profileViewModel.bio.collectAsState()
    val avatarUri by profileViewModel.avatarUri.collectAsState()

    // Editing states
    var editedName by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }
    var editedPhone by remember { mutableStateOf("") }
    var editedBio by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        delay(100)
        titleVisible = true
        delay(300)
        contentVisible = true
    }

    // Sync editing states when entering edit mode
    LaunchedEffect(isEditing) {
        if (isEditing) {
            editedName = name
            editedEmail = email
            editedPhone = phone
            editedBio = bio
        }
    }

    // Image picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                profileViewModel.updateAvatarUri(it.toString())
                profileViewModel.saveProfile()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = titleVisible,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        Text(
                            "J.A.R.V.I.S. PROFILE",
                            fontWeight = FontWeight.Bold,
                            color = IronRed,
                            letterSpacing = 1.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = IronRed)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = contentVisible,
                    enter = fadeIn(
                        animationSpec = androidx.compose.animation.core.tween(durationMillis = 600)
                    ) + slideInVertically(initialOffsetY = { it / 2 })
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Avatar with edit overlay
                            Box(
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                // Profile image or emoji
                                Surface(
                                    modifier = Modifier.size(80.dp),
                                    shape = CircleShape,
                                    color = ReactorBlue.copy(alpha = 0.2f)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (avatarUri.isNotEmpty()) {
                                            AsyncImage(
                                                model = Uri.parse(avatarUri),
                                                contentDescription = "Profile Picture",
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Text(
                                                text = "👤",
                                                fontSize = 40.sp
                                            )
                                        }
                                    }
                                }

                                // Camera icon for editing
                                if (isEditing) {
                                    Surface(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clickable {
                                                launcher.launch("image/*")
                                            },
                                        shape = CircleShape,
                                        color = IronRed
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.CameraAlt,
                                                contentDescription = "Change Photo",
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            // Name
                            if (isEditing) {
                                OutlinedTextField(
                                    value = editedName,
                                    onValueChange = { editedName = it },
                                    label = { Text("Name") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = IronRed,
                                        focusedLabelColor = IronRed
                                    )
                                )
                            } else {
                                Text(
                                    text = name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Email
                            if (isEditing) {
                                OutlinedTextField(
                                    value = editedEmail,
                                    onValueChange = { editedEmail = it },
                                    label = { Text("Email") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = IronRed,
                                        focusedLabelColor = IronRed
                                    )
                                )
                            } else {
                                Text(
                                    text = email,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Phone (edit mode only shows field)
                            if (isEditing) {
                                OutlinedTextField(
                                    value = editedPhone,
                                    onValueChange = { editedPhone = it },
                                    label = { Text("Phone") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = IronRed,
                                        focusedLabelColor = IronRed
                                    )
                                )
                            } else if (phone.isNotEmpty()) {
                                Text(
                                    text = phone,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Bio (edit mode only shows field)
                            if (isEditing) {
                                OutlinedTextField(
                                    value = editedBio,
                                    onValueChange = { editedBio = it },
                                    label = { Text("Bio") },
                                    modifier = Modifier.fillMaxWidth(),
                                    minLines = 3,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = IronRed,
                                        focusedLabelColor = IronRed
                                    )
                                )
                            } else if (bio.isNotEmpty()) {
                                Text(
                                    text = bio,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }

                            // Save/Cancel buttons in edit mode
                            if (isEditing) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            profileViewModel.updateName(editedName)
                                            profileViewModel.updateEmail(editedEmail)
                                            profileViewModel.updatePhone(editedPhone)
                                            profileViewModel.updateBio(editedBio)
                                            profileViewModel.saveProfile()
                                            isEditing = false
                                        },
                                        modifier = Modifier.weight(1f).height(40.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = IronRed
                                        )
                                    ) {
                                        Text("SAVE", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                    Button(
                                        onClick = {
                                            profileViewModel.cancelChanges()
                                            isEditing = false
                                        },
                                        modifier = Modifier.weight(1f).height(40.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Gray
                                        )
                                    ) {
                                        Text("CANCEL", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Order History",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(3) { index ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Order #${1000 + index}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Dec ${20 - index}, 2024",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = "$${(100 + index * 50)}.00",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            }
                            if (index < 2) {
                                HorizontalDivider(
                                    color = Color(0xFFE5E7EB)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Settings",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SettingItem(
                            icon = Icons.Default.Settings,
                            title = "Notifications",
                            subtitle = "Manage notification preferences"
                        )
                        HorizontalDivider(
                            color = Color(0xFFE5E7EB)
                        )
                        SettingItem(
                            icon = Icons.Default.Settings,
                            title = "Privacy",
                            subtitle = "Control your privacy settings"
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        navController.navigate("welcome") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444)
                    )
                ) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = "Logout",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Logout")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = title,
            tint = Primary,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
