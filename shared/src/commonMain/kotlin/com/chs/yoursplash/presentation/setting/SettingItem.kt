package com.chs.yoursplash.presentation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingItem(
    info: Pair<String, String>,
    subTitle: String,
    clickAble: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .clickable { clickAble() }
    ) {
        Text(
            text = info.first,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subTitle,
            fontSize = 18.sp,
            color = Color.Gray,
        )
    }
}