import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIApplication
import presentation.browse.BrowseApp
import util.IosNavigator

fun BrowseViewController(
    info: Pair<String, String>
) = ComposeUIViewController {
    val navigator = IosNavigator()
    val window = UIApplication.sharedApplication.keyWindow()
    navigator.bind(window?.rootViewController()!!)
    BrowseApp(info, navigator)
}
