package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            YourSplashTheme {
                Scaffold(
                    topBar = {
                        TopBar(
                            onNavigationIconClick = {
                                 scope.launch {
                                     scaffoldState.drawerState.open()
                                 }
                            }
                        )
                    },
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "Home",
                                    title = "Home",
                                    icon = Icons.Default.Home
                                )
                            ),
                            onItemClick = {

                            }
                        )
                    }
                ) {

                }
            }
        }
    }
}


@Composable
fun TopBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = { onNavigationIconClick }) {
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
        }, backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )
}


@Composable
fun BottomBar() {
    BottomNavigation {

    }
}