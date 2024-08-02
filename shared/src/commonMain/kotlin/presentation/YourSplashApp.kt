package presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.main.MainTopBar
import com.chs.yoursplash.presentation.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.main.MainNavHost

@Composable
fun YourSplashApp() {
    val navController: NavHostController = rememberNavController()

    var searchQuery: String by remember { mutableStateOf("") }
    val viewModel = koinViewModel<MainViewModel>()
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
}