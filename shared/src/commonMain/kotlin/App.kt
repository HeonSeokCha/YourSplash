import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import presentation.YourSplashApp
import presentation.ui.theme.Red500
import util.Navigator

@Composable
fun App(
   navigator: Navigator
) {
    val colors = lightColorScheme(
        primary = Red500,
        secondary = Red500
    )

    MaterialTheme(colorScheme = colors) {
        KoinContext {
            YourSplashApp(navigator = navigator)
        }
    }
}