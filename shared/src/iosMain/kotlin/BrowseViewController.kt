import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UINavigationController
import presentation.browse.BrowseApp

fun BrowseViewController(
    controller: UINavigationController,
    info: Pair<String, String>
) = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {

    BrowseApp(
        info = info,
        onBack = {
            controller.popViewControllerAnimated(true)
        }
    )
}
