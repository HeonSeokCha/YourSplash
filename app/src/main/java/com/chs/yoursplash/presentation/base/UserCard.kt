package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.util.BlurHashDecoder
import com.chs.yoursplash.util.Constants

@Composable
fun UserCard(
    userInfo: User?,
    loadQuality: String,
    userClickAble: (userName: String) -> Unit,
    photoClickAble: (photoId: String) -> Unit
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                userClickAble(userInfo?.userName ?: "")
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(100))
                .placeholder(
                    visible = userInfo == null,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(userInfo?.photoProfile?.large)
                .crossfade(true)
                .build(),
            contentDescription = null
        )

        Column {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = userInfo?.name ?: "",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(top = 8.dp, end = 8.dp)
            ) {
                if (userInfo?.photos != null && userInfo.photos.isNotEmpty()) {
                    items(
                        count = userInfo.photos.size,
                        key = { userInfo.photos[it].id }
                    ) { idx ->
                        AsyncImage(
                            modifier = Modifier
                                .size(90.dp, 190.dp)
                                .clip(RoundedCornerShape(15))
                                .placeholder(
                                    visible = userInfo == null,
                                    highlight = PlaceholderHighlight.shimmer()
                                ).clickable {
                                    photoClickAble(userInfo.photos[idx].id)
                                },
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Constants.getPhotoQualityUrl(
                                    userInfo.photos[idx].urls,
                                    loadQuality
                                ))
                                .crossfade(true)
                                .build(),

                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            placeholder = Constants.getPlaceHolder(userInfo.photos[idx].blurHash)
                        )
                    }
                }

            }
        }
    }
}