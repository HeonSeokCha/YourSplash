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
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    photoInfo: UnSplashImage
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 4.dp,
                    bottom = 8.dp
                )
                .clickable {
                    context.startActivity(
                        Intent(context, BrowseActivity::class.java).apply {
                            putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                            putExtra(Constants.TARGET_ID, photoInfo.user.id)
                        }
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100)),
                model = photoInfo.user.photoProfile.large,
                placeholder = ColorPainter(photoInfo.color.color),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = photoInfo.user.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    context.startActivity(
                        Intent(context, BrowseActivity::class.java).apply {
                            putExtra(Constants.TARGET_TYPE, Constants.TARGET_PHOTO)
                            putExtra(Constants.TARGET_ID, photoInfo.id)
                        }
                    )
                }
                .background(color = photoInfo.color.color),
            model = photoInfo.urls.small,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(photoInfo.color.color),
            contentDescription = null
        )
    }
}