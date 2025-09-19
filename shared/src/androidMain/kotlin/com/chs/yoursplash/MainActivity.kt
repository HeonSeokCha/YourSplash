package com.chs.yoursplash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.YourSplashApp
import com.chs.yoursplash.util.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourSplashApp {
                startActivity(
                    Intent(this, BrowseActivity::class.java).apply {
                        when (it) {
                            is BrowseInfo.Collection -> {
                                this.putExtra(Constants.TARGET_TYPE, Constants.TARGET_COLLECTION)
                                this.putExtra(Constants.TARGET_ID, it.id)
                            }

                            is BrowseInfo.Photo -> {
                                this.putExtra(Constants.TARGET_TYPE, Constants.TARGET_PHOTO)
                                this.putExtra(Constants.TARGET_ID, it.id)
                            }

                            is BrowseInfo.User -> {
                                this.putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                                this.putExtra(Constants.TARGET_ID, it.name)
                            }

                            is BrowseInfo.PhotoTag -> {
                                this.putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                                this.putExtra(Constants.TARGET_ID, it.tag)
                            }
                        }
                    }
                )
            }
        }
    }
}

