package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserDetailScreen(
    userName: String,
    navController: NavHostController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val tabList = mutableListOf<String>()

    LaunchedEffect(context, viewModel) {
        viewModel.getUserDetail(userName)
        viewModel.getUserDetailPhoto(userName)
        viewModel.getUserDetailLikes(userName)
        viewModel.getUserDetailCollections(userName)
    }

    if (state.userDetailInfo?.totalPhotos != 0) {
        tabList.add("PHOTOS")
    }

    if (state.userDetailInfo?.totalLikes != 0) {
        tabList.add("LIKES")
    }

    if (state.userDetailInfo?.totalCollections != 0) {
        tabList.add("COLLECTIONS")
    }

    BoxWithConstraints {
        val screenHeight = maxHeight
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState
        ) {
            item {
                UserDetailInfo(userInfo = state.userDetailInfo)
            }
            item {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = Purple200
                        )
                    }
                ) {
                    tabList.forEachIndexed { index, title ->
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
                    modifier = Modifier
                        .height(screenHeight)
                        .nestedScroll(remember {
                            object : NestedScrollConnection {
                                override fun onPreScroll(
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    return if (available.y > 0) Offset.Zero else Offset(
                                        x = 0f,
                                        y = -scrollState.dispatchRawDelta(-available.y)
                                    )
                                }
                            }
                        }),
                    count = tabList.size,
                    state = pagerState,
                    userScrollEnabled = false,
                ) { pager ->
                    when (pager) {
                        0 -> {
                            if (tabList[0] == "PHOTOS") {
                                UserDetailPhotoScreen(
                                    context = context,
                                    navController = navController,
                                    state.userDetailPhotoList?.collectAsLazyPagingItems(),
                                    state.loadQuality
                                )
                            } else if (tabList[0] == "LIKES") {
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
                            if (tabList[1] == "LIKES") {
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
            model = userInfo?.profileImage?.large,
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