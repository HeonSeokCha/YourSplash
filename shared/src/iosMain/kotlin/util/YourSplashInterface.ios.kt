package util

import BrowseViewController
import MainViewController
import androidx.compose.ui.interop.UIKitViewController
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UIViewController
import platform.UIKit.modalInPresentation
import platform.UIKit.navigationController

actual interface Navigator {
    actual fun navigateToSecondActivity(type: String, id: String)
    actual fun finish()
}

class IosNavigator : Navigator {
    private var rootViewController: UIViewController? = null

    fun bind(rootViewController: UIViewController) {
        this.rootViewController = rootViewController
    }

    override fun navigateToSecondActivity(
        type: String,
        id: String
    ) {

        val secondViewController = BrowseViewController(
            controller = rootViewController!!,
            info = type to id
        )


        rootViewController?.presentViewController(
            secondViewController,
            animated = true,
            completion = null
        )
    }

    override fun finish() {
        rootViewController?.dismissViewControllerAnimated(true) {}
    }
}