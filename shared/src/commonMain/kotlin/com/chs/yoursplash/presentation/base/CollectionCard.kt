package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
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
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.lorem_ipsum
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.toCommaFormat
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

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
                    .clip(RoundedCornerShape(100))
                    .shimmer(visible = collectionInfo == null),
                url = collectionInfo?.user?.photoProfile?.large
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier.shimmer(collectionInfo == null),
                text = collectionInfo?.user?.name ?: stringResource(Res.string.lorem_ipsum),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        CollectionCard(collectionInfo = collectionInfo) {
            onCollection(it)
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
        CollectionCard(collectionInfo = collectionInfo) {
            collectionClickAble(it)
        }
    }
}

@Composable
private fun CollectionCard(
    collectionInfo: UnSplashCollection?,
    collectionClickAble: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ShimmerImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(end = 4.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmer(visible = collectionInfo == null)
                .drawWithContent {
                    drawContent()
                    if (collectionInfo != null) {
                        drawRect(
                            brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)),
                            alpha = 0.3f
                        )
                    }
                }
                .clickable {
                    if (collectionInfo == null) return@clickable
                    collectionClickAble(collectionInfo.id)
                },
            url = collectionInfo?.previewPhotos?.get(0)?.urls
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .shimmer(collectionInfo == null),
                text = collectionInfo?.title ?: Constants.TEXT_PREVIEW,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .shimmer(collectionInfo == null),
                text = if (collectionInfo == null) {
                    Constants.TEXT_PREVIEW
                } else {
                    "${collectionInfo.totalPhotos.toCommaFormat()} Photos"
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}