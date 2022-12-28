package com.chs.yoursplash.presentation.setting

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 64.dp,
                top = 16.dp
            )
    ) {
        Text(
            text = "Quality",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingItem(
            title = "Load Quality",
            subTitle = viewModel.state.loadQualityValue,
        ) {
        }

        SettingItem(
            title = "Download Quality",
            subTitle = viewModel.state.downLoadQualityValue,
        ) {
        }

        SettingItem(
            title = "Wallpaper Quality",
            subTitle = viewModel.state.wallpaperQualityValue,
        ) {
        }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                buttons = {
                    Button(
                        onClick = {
                            openDialog = false
                        }) {
                        Text("CONFIRM")
                    }
                },title = {
                    Column {

                    }
                }
            )
        }
    }
}
