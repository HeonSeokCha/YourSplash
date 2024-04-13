package com.chs.yoursplash.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.yoursplash.presentation.main.collection.CollectionScreen
import com.chs.yoursplash.presentation.main.home.HomeScreen
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.presentation.setting.SettingScreen
import com.chs.yoursplash.util.SearchFilter
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavScreen.HomeScreen.route
    ) {
        composable(BottomNavScreen.HomeScreen.route) {
            HomeScreen()
        }

        composable(BottomNavScreen.CollectionScreen.route) {
            CollectionScreen()
        }

//        composable(MainScreens.SearchScreen.route) {
//            SearchScreen(
//                searchKeyWord = searchKeyword,
//                searchFilter = searchFilter,
//                modalClick = {
//                    scope.launch {
//                    }
//                }, onBack = {
//                    searchKeyword = ""
//                    searchFilter = SearchFilter()
//                }
//            )
//        }

        composable(MainScreens.SettingScreen.route) {
            SettingScreen()
        }
    }
}