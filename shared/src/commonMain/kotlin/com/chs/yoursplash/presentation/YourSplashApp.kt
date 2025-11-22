package com.chs.yoursplash.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.main.MainNavHost
import com.chs.yoursplash.presentation.main.MainScreens
import com.chs.yoursplash.presentation.main.MainTopBar
import com.chs.yoursplash.presentation.main.MainViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun YourSplashApp(onBrowseInfo: (BrowseInfo) -> Unit) {
    val module = SerializersModule {
        polymorphic(MainScreens::class) {
            subclass(MainScreens.PhotoScreen::class, MainScreens.PhotoScreen.serializer())
            subclass(MainScreens.CollectionScreen::class, MainScreens.CollectionScreen.serializer())
            subclass(MainScreens.SearchScreen::class, MainScreens.SearchScreen.serializer())
            subclass(MainScreens.SettingScreen::class, MainScreens.SettingScreen.serializer())
        }
    }

    val config = SavedStateConfiguration { serializersModule = module }
    val backStack = rememberNavBackStack(configuration = config, MainScreens.PhotoScreen)
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val textFieldState = rememberTextFieldState()

    Scaffold(
        topBar = {
            MainTopBar(
                backStack = backStack,
                textFieldState = textFieldState,
                searchHistoryList = state.searchHistory,
                onQueryChange = {
                    if (it.isNotEmpty()) {
                        viewModel.insertSearchHistory(it)
                    }
                    textFieldState.edit { replace(0, length, it) }
                    viewModel.updateSearchQuery(it)
                },
                onDeleteSearchHistory = {
                    viewModel.deleteSearchHistory(it)
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        MainNavHost(
            modifier = Modifier.padding(it),
            backStack = backStack,
            searchQuery = state.searchQuery,
            onBrowse = onBrowseInfo
        )
    }
}