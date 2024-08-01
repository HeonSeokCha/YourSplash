package presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.presentation.main.MainTopBar

@Composable
fun YourSplashApp() {
    val navController: NavHostController = rememberNavController()

    var searchQuery: String by remember { mutableStateOf("") }
    val state = viewModel.state
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