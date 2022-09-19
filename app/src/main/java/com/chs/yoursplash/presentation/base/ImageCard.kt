package com.chs.yoursplash.presentation.base

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.presentation.image_detail.ImageDetailActivity
import com.chs.yoursplash.presentation.user.UserDetailActivity
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    image: UnSplashImage
) {

    val context = LocalContext.current

    Column (modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 4.dp,
                    bottom = 8.dp
                )
                .clickable {
                    context.startActivity(
                        Intent(context, UserDetailActivity::class.java)
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100)),
                model = image.user.photoProfile.large,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = image.user.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    context.startActivity(
                        Intent(context, ImageDetailActivity::class.java)
                    )
                }
                .background(color = image.color.color),
            model = image.urls.small,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}