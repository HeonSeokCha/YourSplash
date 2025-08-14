import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.yoursplash.presentation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(
    navController: NavHostController,
    onBack: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination
    TopAppBar(
        title = { },
        navigationIcon = {
            if (currentDestination?.hasRoute(Screens.PhotoTagResultScreen::class) == true) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            } else {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        }
    )
}
