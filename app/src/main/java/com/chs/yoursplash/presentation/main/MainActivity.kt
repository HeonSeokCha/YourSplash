package com.chs.yoursplash.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.main.home.HomeScreen
import com.chs.yoursplash.presentation.search.SearchActivity
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            YourSplashTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBar(
                            onNavigationIconClick = {
                                 scope.launch {
                                     scaffoldState.drawerState.open()
                                 }
                            }
                        )
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "Setting",
                                    title = "Setting",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "About",
                                    title = "About",
                                    icon = Icons.Default.Home
                                )
                            ),
                            onItemClick = {
                                when (it.id) {
                                    "Setting" -> {

                                    }
                                    "About" -> {

                                    }
                                }
                            }
                        )
                    }
                ) {
                    HomeScreen()
                }
            }
        }
    }
}


@Composable
fun TopBar(
    onNavigationIconClick: () -> Unit
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                onNavigationIconClick()
            }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = {
                context.startActivity(
                    Intent(context, SearchActivity::class.java)
                )
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