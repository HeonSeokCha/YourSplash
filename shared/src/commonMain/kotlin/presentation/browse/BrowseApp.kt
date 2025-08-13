package presentation.browse

import ImageDetailTopBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BrowseApp(
    info: Pair<String, String>,
    onBack: () -> Unit
) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            ImageDetailTopBar(
                navController = navController,
                onBack = onBack
            )
        }
    ) {
        BrowseNavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            navController = navController,
            type = info.first,
            id = info.second
        )
    }
}