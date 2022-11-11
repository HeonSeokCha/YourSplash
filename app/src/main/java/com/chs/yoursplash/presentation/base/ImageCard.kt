package com.chs.yoursplash.presentation.base

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.util.BlurHashDecoder
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    photoInfo: Photo?,
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
                ).clickable {
                    userClickAble(photoInfo?.user?.userName ?: "")
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100)),
                model = photoInfo?.user?.photoProfile?.large,
                placeholder = ColorPainter(photoInfo?.color?.color ?: Color.White),
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
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                   photoClickAble(photoInfo?.id ?: "")
                },
            model = photoInfo?.urls?.small,
            contentScale = ContentScale.Crop,
            placeholder = BitmapPainter(
                BlurHashDecoder.decode(
                    blurHash = photoInfo?.blurHash,
                    width = 40,
                    height = 60,
                )!!.asImageBitmap()
            ),
            contentDescription = null,
        )
    }
}