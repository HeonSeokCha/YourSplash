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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    photoInfo: Photo?,
    loadQuality: String = "Regular",
    userClickAble: (userName: String) -> Unit,
    photoClickAble: (photoId: String) -> Unit
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
                    if (photoInfo?.user?.userName != null) {
                        userClickAble(photoInfo.user.userName)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100))
                    .placeholder(
                        visible = photoInfo == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoInfo?.user?.photoProfile?.large)
                    .crossfade(true)
                    .build(),
                placeholder = ColorPainter(Color.Gray),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = photoInfo?.user?.name ?: "...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
                .clip(RoundedCornerShape(10.dp))
                .placeholder(
                    visible = photoInfo == null,
                    highlight = PlaceholderHighlight.shimmer()
                )
                .clickable {
                    if (photoInfo?.id != null) {
                        photoClickAble(photoInfo.id)
                    }
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.getPhotoQualityUrl(photoInfo?.urls, loadQuality))
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            placeholder = Constants.getPlaceHolder(photoInfo?.blurHash),
            contentDescription = null,
        )
    }
}