package com.chs.yoursplash.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    currentBackStack: MainScreens,
    onSearch: (String) -> Unit,
    onNavigateSearch: () -> Unit,
    onNavigateSetting: () -> Unit,
    onClose: () -> Unit
) {
    when {
        BottomNavigation.entries.any { it.route == currentBackStack } -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Splash",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }, actions = {
                    IconButton(onClick = onNavigateSearch) {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = onNavigateSetting) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        currentBackStack == MainScreens.SearchScreen -> {
            SearchAppBar(
                onSearch = onSearch,
                onBack = onClose,
            )
        }

        currentBackStack == MainScreens.SettingScreen -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        else -> Unit
    }
}
