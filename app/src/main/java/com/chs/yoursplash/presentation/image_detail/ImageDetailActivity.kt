package com.chs.yoursplash.presentation.image_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourSplashTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = "This is ImageDetailActivity")
                }
            }
        }
    }
}