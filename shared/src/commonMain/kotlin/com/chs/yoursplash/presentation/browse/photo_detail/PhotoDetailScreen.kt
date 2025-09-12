package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.util.Constants

@Composable
fun ImageDetailScreen(
    state: PhotoDetailState,
    onClose: () -> Unit,
    onNavigate: (Screens) -> Unit
) {
    val scrollState = rememberScrollState()
    val lazyVerticalStaggeredState = rememberLazyStaggeredGridState()

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            Column {
                ShimmerImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    url = state.imageDetailInfo?.urls
                )

                ItemUserInfoFromPhotoDetail(state.imageDetailInfo?.user) {
                    onNavigate(it)
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
        },
        isShowTopBar = false,
        onCloseClick = onClose
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyVerticalStaggeredState,
            columns = StaggeredGridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
        ) {
            if (state.imageRelatedList.isNotEmpty()) {
                items(count = state.imageRelatedList.size) { idx ->
                    val item = state.imageRelatedList[idx]
                    ShimmerImage(
                        modifier = Modifier
                            .width(130.dp)
                            .height(280.dp)
                            .clickable { onNavigate(Screens.ImageDetailScreen(item.id)) },
                        url = item.urls
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemUserInfoFromPhotoDetail(
    info: User?,
    onNavigate: (Screens) -> Unit
) {
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
                    if (info?.userName != null) {
                        onNavigate(
                            Screens.UserDetailScreen(info.userName)
                        )
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100)),
                url = info?.photoProfile?.large
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier
                    .shimmer(visible = info == null),
                text = info?.name ?: Constants.TEXT_PREVIEW,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}