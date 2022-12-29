package com.chs.yoursplash.presentation.setting

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.yoursplash.presentation.ui.theme.Purple500
import com.chs.yoursplash.util.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(false) }
    var selectButtonTitle by remember { mutableStateOf("") }
    var selectButton by remember { mutableStateOf(viewModel.state.loadQualityValue) }

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
            selectButtonTitle = it
            openDialog = true
        }

        SettingItem(
            title = "Download Quality",
            subTitle = viewModel.state.downLoadQualityValue,
        ) {
            selectButtonTitle = it
            openDialog = true
        }

        SettingItem(
            title = "Wallpaper Quality",
            subTitle = viewModel.state.wallpaperQualityValue,
        ) {
            selectButtonTitle = it
            openDialog = true
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
                        Constants.QUALITY_LIST.forEach {
                            val isSelected = it.value == selectButton
                            val color = RadioButtonDefaults.colors(
                                selectedColor = Purple500,
                                unselectedColor = Color.LightGray
                            )
                            Row {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectButton = it.value }
                                )
                                Text(text = it.key)
                            }
                        }
                    }
                }
            )
        }
    }
}
