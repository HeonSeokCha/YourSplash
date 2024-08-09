import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.LocalPlatformContext
import com.chs.yoursplash.presentation.Screens
import util.Navigator
import util.fromScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(
    navigator: Navigator,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.fromScreenRoute()
    TopAppBar(
        title = { },
        navigationIcon = {
            if (currentDestination is Screens.PhotoTagResultScreen) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            } else {
                IconButton(onClick = {
                    navigator.finish()
                }) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        }
    )
}
