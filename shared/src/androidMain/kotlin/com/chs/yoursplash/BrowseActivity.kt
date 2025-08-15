package com.chs.yoursplash

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chs.yoursplash.presentation.browse.BrowseApp
import com.chs.yoursplash.util.Constants

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
                onBack = {
                    finish()
                }
            )
        }
    }
}
