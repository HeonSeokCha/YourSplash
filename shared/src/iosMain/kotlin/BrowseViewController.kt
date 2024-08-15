import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UIViewController
import presentation.browse.BrowseApp
import util.IosNavigator

fun BrowseViewController(
    controller: UIViewController,
    info: Pair<String, String>
) = ComposeUIViewController {

    val navigator = IosNavigator().apply {
        this.bind(controller)
    }
    BrowseApp(info, navigator)
}
