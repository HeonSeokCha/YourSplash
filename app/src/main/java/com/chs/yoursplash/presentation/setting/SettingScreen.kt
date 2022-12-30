package com.chs.yoursplash.presentation.setting

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.yoursplash.presentation.ui.theme.Purple500
import com.chs.yoursplash.util.Constants

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(false) }
    var selectButtonTitle by remember { mutableStateOf("" to "") }

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
            selectButtonTitle = Constants.PREFERENCE_KEY_LOAD_QUALITY to it
            openDialog = true
        }

        SettingItem(
            title = "Download Quality",
            subTitle = viewModel.state.downLoadQualityValue,
        ) {
            selectButtonTitle = Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY to it
            openDialog = true
        }

        SettingItem(
            title = "Wallpaper Quality",
            subTitle = viewModel.state.wallpaperQualityValue,
        ) {
            selectButtonTitle = Constants.PREFERENCE_KEY_WALLPAPER_QUALITY to it
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
                            Log.e("ABC", selectButtonTitle.toString())
                            viewModel.putSettingPreference(
                                selectButtonTitle.first,
                                selectButtonTitle.second
                            )
                            openDialog = false
                        }) {
                        Text("APPLY")
                    }
                },title = {
                    Column {
                        Constants.QUALITY_LIST.forEach {
                            val isSelected = it.value == selectButtonTitle.second
                            val color = RadioButtonDefaults.colors(
                                selectedColor = Purple500,
                                unselectedColor = Color.LightGray
                            )
                            Row {
                                RadioButton(
                                    colors = color,
                                    selected = isSelected,
                                    onClick = {
                                        selectButtonTitle = selectButtonTitle.copy(second = it.value)
                                    }
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
