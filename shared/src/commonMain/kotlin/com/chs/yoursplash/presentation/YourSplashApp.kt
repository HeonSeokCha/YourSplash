package com.chs.yoursplash.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.chs.yoursplash.di.KoinModule
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.main.MainNavDisplay
import com.chs.yoursplash.presentation.main.MainScreens
import com.chs.yoursplash.presentation.main.MainTopBar
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

@Composable
fun YourSplashApp(onBrowseInfo: (BrowseInfo) -> Unit) {
    KoinApplication(koinConfiguration<KoinModule>()){
        val backStack: SnapshotStateList<MainScreens> = remember { mutableStateListOf(MainScreens.PhotoScreen) }
        var currentSearchQuery by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                MainTopBar(
                    backStack = backStack,
                    onSearch = { currentSearchQuery = it }
                )
            },
            bottomBar = { BottomBar(backStack) }
        ) {
            MainNavDisplay(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                backStack = backStack,
                onBrowse = onBrowseInfo,
                searchQuery = currentSearchQuery,
            )
        }
    }
}