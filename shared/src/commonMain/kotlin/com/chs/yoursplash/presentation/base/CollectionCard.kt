package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.util.Constants

@Composable
fun CollectionInfoCard(
    collectionInfo: UnSplashCollection?,
    onCollection: (String) -> Unit = {},
    onUser: (String) -> Unit = {}
) {
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
                    if (collectionInfo != null) {
                        onUser(collectionInfo.user.userName)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100)),
                url = collectionInfo?.user?.photoProfile?.large
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = collectionInfo?.user?.name ?: "...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        if (collectionInfo != null) {
            CollectionCard(
                collectionInfo = collectionInfo,
            ) {
                onCollection(it)
            }
        }
    }
}

@Composable
fun CollectionSimpleCard(
    collectionInfo: UnSplashCollection?,
    collectionClickAble: (collectionId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (collectionInfo != null) {
            CollectionCard(collectionInfo = collectionInfo) {
                collectionClickAble(it)
            }
        }
    }
}

@Composable
private fun CollectionCard(
    collectionInfo: UnSplashCollection,
    collectionClickAble: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                collectionClickAble(collectionInfo.id)
            }
    ) {

        ShimmerImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(end = 4.dp)
                .clip(RoundedCornerShape(10.dp)),
            url = collectionInfo.previewPhotos?.get(0)?.urls
        )
    }
}