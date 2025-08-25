package com.chs.yoursplash.presentation.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.presentation.ui.theme.Purple500
import com.chs.yoursplash.util.Constants

@Composable
fun SettingScreen(
    state: SettingState,
    onEvent: (SettingEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 64.dp,
                end = 64.dp,
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
            subTitle = state.loadQualityValue.name,
            clickAble = { onEvent(SettingEvent.ClickLoad) }
        )

        SettingItem(
            title = "Download Quality",
            subTitle = state.downLoadQualityValue.name,
            clickAble = { onEvent(SettingEvent.ClickDownload) }
        )

        SettingItem(
            title = "Wallpaper Quality",
            subTitle = state.wallpaperQualityValue.name,
            clickAble = { onEvent(SettingEvent.ClickWallpaper) }
        )

        if (state.showDialog) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onDismissRequest = { onEvent(SettingEvent.CloseDialog) },
                confirmButton = {
                    Button(
                        onClick = { onEvent(SettingEvent.ClickSave) }
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    colors = color,
                                    selected = isSelected,
                                    onClick = {
                                        onEvent(SettingEvent.SelectValue(it))
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
