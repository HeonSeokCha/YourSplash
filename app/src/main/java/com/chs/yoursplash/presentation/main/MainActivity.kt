package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourSplashTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                }
            }
        }
    }
}


@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp
            )
        },    navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = {
                /* doSomething() */
            }) {
                Icon(
                    imageVector = Icons.TwoTone.Search,
                    contentDescription = null
                )
            }
        }, backgroundColor = colorResource(id = R.color.purple_200),
        contentColor = Color.White
    )
}


@Composable
fun BottomBar() {
    BottomNavigation {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopBar()
}