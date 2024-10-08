import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import util.IosNavigator

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val navigator = IosNavigator()
    val window = UIApplication.sharedApplication.keyWindow()
    navigator.bind(window?.rootViewController()!!)
    App(navigator)
 }