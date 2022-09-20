package com.chs.yoursplash.presentation.image_detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.search.SearchActivity
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourSplashTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        ImageDetailTopBar()
                    }
                ) {
                    ImageDetailScreen()
                }
            }
        }
    }
}

@Composable
fun ImageDetailTopBar() {
    val activity = (LocalContext.current as? Activity)
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = {
                activity?.finish()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }, backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 10.dp
    )
}