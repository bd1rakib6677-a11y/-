package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.*
import com.example.ui.theme.FF_Card_Bg
import com.example.ui.theme.FF_Dark_Bg
import com.example.ui.theme.FF_Orange
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Supports borderless status/navigation screen view edge to edge
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                var currentScreen = viewModel.currentScreen
                val loggedUser = viewModel.currentUser

                // Display success notifications
                viewModel.successMessage?.let { msg ->
                    LaunchedEffect(msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                        viewModel.clearMessages()
                    }
                }

                // Display error notifications
                viewModel.errorMessage?.let { err ->
                    LaunchedEffect(err) {
                        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
                        viewModel.clearMessages()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Render bottom navigation only if user is logged in
                        if (loggedUser != null) {
                            AppBottomNavigation(
                                currentScreen = currentScreen,
                                isAdmin = loggedUser.isAdmin,
                                onNavigate = { viewModel.currentScreen = it }
                            )
                        }
                    },
                    containerColor = FF_Dark_Bg
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // Premium crossfade transition for smooth page changes
                        Crossfade(
                            targetState = currentScreen,
                            animationSpec = tween(durationMillis = 400),
                            label = "screen_navigation_fade"
                        ) { screen ->
                            when (screen) {
                                is Screen.Login -> LoginScreen(viewModel)
                                is Screen.SignUp -> SignUpScreen(viewModel)
                                is Screen.Home -> HomeScreen(viewModel)
                                is Screen.MyMatches -> MyMatchesScreen(viewModel)
                                is Screen.Wallet -> WalletScreen(viewModel)
                                is Screen.AdminPanel -> AdminPanelScreen(viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBottomNavigation(
    currentScreen: Screen,
    isAdmin: Boolean,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = FF_Card_Bg,
        tonalElevation = 8.dp,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBarItem(
            selected = currentScreen is Screen.Home,
            onClick = { onNavigate(Screen.Home) },
            icon = { Text("⚔️", fontSize = 18.sp) },
            label = { Text("টুর্নামেন্ট", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = FF_Orange,
                selectedTextColor = FF_Orange,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = FF_Orange.copy(alpha = 0.15f)
            )
        )

        NavigationBarItem(
            selected = currentScreen is Screen.MyMatches,
            onClick = { onNavigate(Screen.MyMatches) },
            icon = { Text("🎮", fontSize = 18.sp) },
            label = { Text("আমার ম্যাচ", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = FF_Orange,
                selectedTextColor = FF_Orange,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = FF_Orange.copy(alpha = 0.15f)
            )
        )

        NavigationBarItem(
            selected = currentScreen is Screen.Wallet,
            onClick = { onNavigate(Screen.Wallet) },
            icon = { Text("💰", fontSize = 18.sp) },
            label = { Text("ওয়ালেট & উইথড্র", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = FF_Orange,
                selectedTextColor = FF_Orange,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = FF_Orange.copy(alpha = 0.15f)
            )
        )

        if (isAdmin) {
            NavigationBarItem(
                selected = currentScreen is Screen.AdminPanel,
                onClick = { onNavigate(Screen.AdminPanel) },
                icon = { Text("🔧", fontSize = 18.sp) },
                label = { Text("এডমিন প্যানেল", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FF_Orange,
                    selectedTextColor = FF_Orange,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = FF_Orange.copy(alpha = 0.15f)
                )
            )
        }
    }
}
