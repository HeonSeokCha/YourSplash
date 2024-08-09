import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin
import presentation.browse.BrowseApp

fun BrowseViewController(
    info: Pair<String, String>
) = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { BrowseApp(info) }
