package com.chs.yoursplash.presentation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.presentation.ui.theme.Purple500


@Composable
fun SettingScreenRoot(viewModel: SettingViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SettingScreen(
    state: SettingState,
    onEvent: (SettingIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 32.dp,
                end = 64.dp,
                top = 16.dp
            )
    ) {
        Text(
            text = "Quality",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 32.dp)
        ) {
            SettingItem(
                title = "Load Quality",
                subTitle = state.loadQualityValue.name,
                clickAble = { onEvent(SettingIntent.ClickLoad) }
            )

            SettingItem(
                title = "Download Quality",
                subTitle = state.downLoadQualityValue.name,
                clickAble = { onEvent(SettingIntent.ClickDownload) }
            )

            SettingItem(
                title = "Wallpaper Quality",
                subTitle = state.wallpaperQualityValue.name,
                clickAble = { onEvent(SettingIntent.ClickWallpaper) }
            )
        }

        if (state.showDialog) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onDismissRequest = { onEvent(SettingIntent.CloseDialog) },
                confirmButton = {
                    Button(
                        onClick = { onEvent(SettingIntent.ClickSave) }
                    ) {
                        Text(text = "APPLY")
                    }
                },title = {
                    Text(
                        text = state.selectSettingTitle,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }, text = {
                    Column {
                        LoadQuality.entries.forEach {
                            val isSelected = it == state.selectValue
                            val color = RadioButtonDefaults.colors(
                                selectedColor = Purple500,
                                unselectedColor = Color.LightGray
                            )
                            Row (
                                modifier = Modifier
                                    .clickable {
                                        onEvent(SettingIntent.SelectValue(it))
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    colors = color,
                                    selected = isSelected,
                                    onClick = {
                                        onEvent(SettingIntent.SelectValue(it))
                                    }
                                )

                                Text(
                                    text = it.name,
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
