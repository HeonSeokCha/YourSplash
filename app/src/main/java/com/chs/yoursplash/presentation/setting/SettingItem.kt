package com.chs.yoursplash.presentation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingItem(
    title: String,
    subTitle: String,
    clickAble: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .clickable {
            clickAble(title)
        }
    ) {
        Text(
            text = title,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subTitle,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Light
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PreViewSettingItem() {
    SettingItem(title = "Test", subTitle = "This is Test Item") { }
}