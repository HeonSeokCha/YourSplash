package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.PlaceholderHighlight
import com.chs.yoursplash.presentation.base.placeholder
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailScreen(
    state: UserDetailState,
    onNavigate: (Screens) -> Unit
) {
    val pagerState = rememberPagerState { state.userTabLabList.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (state.isError) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = state.errorMessage ?: "UnknownError"
            )
        } else {
            UserDetailInfo(userInfo = state.userDetailInfo)

            if (state.userTabLabList.isNotEmpty()) {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.White,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Purple200
                        )
                    }
                ) {
                    state.userTabLabList.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    color = Purple200,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 13.sp
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                ) { pager ->
                    when (pager) {
                        0 -> {
                            UserDetailPhotoScreen(
                                state.userDetailPhotoList?.collectAsLazyPagingItems(),
                                state.loadQuality
                            ) {
                                onNavigate(it)
                            }
                        }

                        1 -> {
                            UserDetailLikeScreen(
                                state.userDetailLikeList?.collectAsLazyPagingItems(),
                                state.loadQuality
                            ) {
                                onNavigate(it)
                            }
                        }

                        2 -> {
                            UserDetailCollectionScreen(
                                state.userDetailCollection?.collectAsLazyPagingItems(),
                                state.loadQuality
                            ) {
                                onNavigate(it)
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun UserDetailInfo(userInfo: UserDetail?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp, 100.dp)
                .clip(RoundedCornerShape(100))
                .placeholder(
                    visible = userInfo == null,
                    highlight = PlaceholderHighlight.Companion.shimmer()
                ),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(userInfo?.profileImage?.large)
                .crossfade(true)
                .build(),
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .placeholder(
                        visible = userInfo == null,
                        highlight = PlaceholderHighlight.Companion.shimmer()
                    ),
                text = userInfo?.name ?: Constants.TEXT_PREVIEW,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UserDetailInfoItem(
                    title = "Photos",
                    text = userInfo?.totalPhotos?.toString() ?: "0"
                )

                UserDetailInfoItem(
                    title = "Likes",
                    text = userInfo?.totalLikes?.toString() ?: "0"
                )

                UserDetailInfoItem(
                    title = "Collections",
                    text = userInfo?.totalCollections?.toString() ?: "0"
                )
            }
        }
    }
}

@Composable
fun UserDetailInfoItem(title: String, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}