package com.chs.yoursplash.presentation.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
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
    }
}