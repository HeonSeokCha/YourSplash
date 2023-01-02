package com.chs.yoursplash.presentation.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    var selectButtonInfo by remember { mutableStateOf("" to "") }
    var selectButtonTitle by remember { mutableStateOf("") }

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
        ) { title, sub ->
            selectButtonTitle = title
            selectButtonInfo = Constants.PREFERENCE_KEY_LOAD_QUALITY to sub
            openDialog = true
        }

        SettingItem(
            title = "Download Quality",
            subTitle = viewModel.state.downLoadQualityValue,
        ) { title, sub ->
            selectButtonTitle = title
            selectButtonInfo = Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY to sub
            openDialog = true
        }

        SettingItem(
            title = "Wallpaper Quality",
            subTitle = viewModel.state.wallpaperQualityValue,
        ) { title, sub ->
            selectButtonTitle = title
            selectButtonInfo = Constants.PREFERENCE_KEY_WALLPAPER_QUALITY to sub
            openDialog = true
        }

        if (openDialog) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onDismissRequest = {
                    openDialog = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.putSettingPreference(
                                selectButtonInfo.first,
                                selectButtonInfo.second
                            )
                            openDialog = false
                        }
                    ) {
                        Text(text = "APPLY")
                    }
                },title = {
                    Text(
                        text = selectButtonTitle,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }, text = {
                    Column {
                        Constants.QUALITY_LIST.forEach {
                            val isSelected = it.value == selectButtonInfo.second
                            val color = RadioButtonDefaults.colors(
                                selectedColor = Purple500,
                                unselectedColor = Color.LightGray
                            )
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    colors = color,
                                    selected = isSelected,
                                    onClick = {
                                        selectButtonInfo = selectButtonInfo.copy(second = it.value)
                                    }
                                )
                                Text(
                                    text = it.key,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}
