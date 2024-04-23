package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.presentation.ui.theme.Purple200
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailScreen(
    navController: NavHostController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val pagerState = rememberPagerState { state.userTabLabList.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
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
                        if (state.userTabLabList[0] == "PHOTOS") {
                            UserDetailPhotoScreen(
                                context = context,
                                navController = navController,
                                state.userDetailPhotoList?.collectAsLazyPagingItems(),
                                state.loadQuality
                            )
                        } else if (state.userTabLabList[0] == "LIKES") {
                            UserDetailLikeScreen(
                                context = context,
                                navController = navController,
                                state.userDetailLikeList?.collectAsLazyPagingItems(),
                                state.loadQuality
                            )
                        } else {
                            UserDetailCollectionScreen(
                                context = context,
                                navController = navController,
                                state.userDetailCollection?.collectAsLazyPagingItems(),
                                state.loadQuality
                            )
                        }
                    }
                    1 -> {
                        if (state.userTabLabList[1] == "LIKES") {
                            UserDetailLikeScreen(
                                context = context,
                                navController = navController,
                                state.userDetailLikeList?.collectAsLazyPagingItems(),
                                state.loadQuality
                            )
                        } else {
                            UserDetailCollectionScreen(
                                context = context,
                                navController = navController,
                                state.userDetailCollection?.collectAsLazyPagingItems(),
                                state.loadQuality
                            )
                        }
                    }
                    2 -> {
                        UserDetailCollectionScreen(
                            context = context,
                            navController = navController,
                            state.userDetailCollection?.collectAsLazyPagingItems(),
                            state.loadQuality
                        )
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
                .clip(RoundedCornerShape(100)),
            model = ImageRequest.Builder(LocalContext.current)
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
                text = userInfo?.userName ?: "Unknown",
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