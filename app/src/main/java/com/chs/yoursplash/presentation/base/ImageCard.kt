package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    image: UnSplashImage
) {
    Column (modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        // TODO: navigate userActivity.
                    },
                model = image.user.photoProfile.medium,
                contentDescription = null
            )
            Text(
                text = image.user.userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    // TODO: navigate imageDetailActivity.
                }
                .background(color = image.color.color),
            model = image.urls.thumb,
            contentDescription = null
        )
    }
}