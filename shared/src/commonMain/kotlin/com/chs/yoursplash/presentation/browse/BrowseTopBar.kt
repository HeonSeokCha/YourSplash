import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.chs.yoursplash.presentation.BrowseScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(
    backStack: NavBackStack<NavKey>,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            if (backStack.last() == BrowseScreens.PhotoTagResultScreen) {
                IconButton(onClick = { backStack.removeLastOrNull() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }  else {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        }
    )
}
