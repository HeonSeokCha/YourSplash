package com.chs.yoursplash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chs.yoursplash.presentation.YourSplashApp
import com.chs.yoursplash.util.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourSplashApp {
                startActivity(
                    Intent(this, BrowseActivity::class.java).apply {
                        this.putExtra(Constants.TARGET_TYPE, it.first)
                        this.putExtra(Constants.TARGET_ID, it.second)
                    }
                )
            }
        }
    }
}

