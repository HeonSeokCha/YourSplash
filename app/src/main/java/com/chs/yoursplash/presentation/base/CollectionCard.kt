package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.util.BlurHashDecoder
import com.chs.yoursplash.util.Constants

@Composable
fun CollectionCard(
    collectionInfo: UnSplashCollection?,
    loadQuality: String,
    userClickAble: (userName: String) -> Unit,
    collectionClickAble: (collectionId: String) -> Unit
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
                    userClickAble(collectionInfo?.user?.userName ?: "")
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100)),
                model = collectionInfo?.user?.photoProfile?.large,
                contentDescription = null
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

        Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(250.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)),
                            alpha = 0.4f
                        )
                    }
                    .clickable {
                        collectionClickAble(collectionInfo?.id ?: "")
                    },
                model = Constants.getPhotoQualityUrl(collectionInfo?.previewPhotos?.get(0)?.urls, loadQuality),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                placeholder = if (collectionInfo?.previewPhotos != null) {
                    BitmapPainter(
                        BlurHashDecoder.decode(blurHash = collectionInfo.previewPhotos[0].blurHash)!!.asImageBitmap()
                    )
                } else { null },
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(
                    text = collectionInfo?.title ?: "...",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${collectionInfo?.totalPhotos ?: 0} Photos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}