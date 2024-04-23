package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.collection.CollectionScreen
import com.chs.yoursplash.presentation.home.HomeScreen
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.presentation.setting.SettingScreen
import com.chs.yoursplash.util.SearchFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var searchQuery: String by remember { mutableStateOf("") }
            val state by viewModel.state.collectAsStateWithLifecycle()

            YourSplashTheme {
                Scaffold(
                    topBar = {
                        MainTopBar(
                            navController = navController,
                            searchHistoryList = state.searchHistory,
                            onQueryChange = {
                                if (it.isNotEmpty()) {
                                    viewModel.insertSearchHistory(it)
                                }
                                searchQuery = it
                            },
                            onDeleteSearchHistory = {
                                viewModel.deleteSearchHistory(it)
                            }
                        )
                    },
                    bottomBar = {
                        BottomBar(navController = navController)
                    },
                ) {
                    MainNavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        searchQuery = searchQuery,
                        onBack = { searchQuery = "" }
                    )
                }
            }
        }
    }
}
