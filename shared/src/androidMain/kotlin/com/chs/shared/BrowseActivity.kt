package com.chs.shared

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import presentation.browse.BrowseApp
import util.AndroidNavigator
import util.Constants
import util.Navigator

class BrowseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!intent.hasExtra(Constants.TARGET_TYPE) || !intent.hasExtra(Constants.TARGET_ID)) {
            Toast.makeText(this, "no info", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            BrowseApp(
                info = intent.getStringExtra(Constants.TARGET_TYPE)!! to
                        intent.getStringExtra(Constants.TARGET_ID)!!,
                navigator = AndroidNavigator(this)
            )
        }
    }
}
