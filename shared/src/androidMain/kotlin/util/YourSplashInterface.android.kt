package util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.chs.shared.BrowseActivity

actual interface Navigator {
    actual fun navigateToSecondActivity(type: String, id: String)
    actual fun finish()
}

class AndroidNavigator(
    private val context: Context
) : Navigator {
    override fun navigateToSecondActivity(
        type: String,
        id: String
    ) {
        context.startActivity(
            Intent(context, BrowseActivity::class.java).apply {
                this.putExtra(Constants.TARGET_TYPE, type)
                this.putExtra(Constants.TARGET_ID, id)
            }
        )
    }

    override fun finish() {
        (context as Activity?)?.finish()
    }
}