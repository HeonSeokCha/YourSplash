package util

import BrowseViewController
import platform.UIKit.UIViewController

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
        val secondViewController = BrowseViewController(type to id)
        rootViewController?.presentViewController(
            secondViewController,
            animated = true,
            completion = null
        )
    }

    override fun finish() {
        rootViewController
    }
}