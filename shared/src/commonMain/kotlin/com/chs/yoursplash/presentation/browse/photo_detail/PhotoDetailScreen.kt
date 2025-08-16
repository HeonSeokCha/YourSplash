package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.util.Constants

@Composable
fun ImageDetailScreen(
    state: PhotoDetailState,
    onNavigate: (Screens) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
    ) {
        if (state.isError) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(state.errorMessage ?: "UnknownError")
            }
        } else {
            item(span = StaggeredGridItemSpan.FullLine) {
                Column {
                    ShimmerImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        url = Constants.getPhotoQualityUrl(
                            state.imageDetailInfo?.urls,
                            state.wallpaperQuality
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    if (state.imageDetailInfo?.user?.userName != null) {
                                        onNavigate(
                                            Screens.UserDetailScreen(state.imageDetailInfo.user.userName)
                                        )
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ShimmerImage(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(100)),
                                url = state.imageDetailInfo?.user?.photoProfile?.large
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = state.imageDetailInfo?.user?.name ?: Constants.TEXT_PREVIEW,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

                    if (state.imageDetailInfo != null) {
                        ImageDetailInfo(state.imageDetailInfo) { selectTag ->
                            onNavigate(
                                Screens.PhotoTagResultScreen(selectTag)
                            )
                        }
                    }
                }
            }

            if (state.imageRelatedList.isNotEmpty()) {
                items(
                    count = state.imageRelatedList.size,
                    key = { state.imageRelatedList[it].id }
                ) { idx ->
                    val item = state.imageRelatedList[idx]
                    ShimmerImage(
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                            .clickable { onNavigate(Screens.ImageDetailScreen(item.id)) },
                        url = Constants.getPhotoQualityUrl(item.urls, state.loadQuality)
                    )
                }
            }
        }
    }
}