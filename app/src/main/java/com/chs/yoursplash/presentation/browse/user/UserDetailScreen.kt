package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.User
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

    LaunchedEffect(context, viewModel) {
        viewModel.getUserDetail(userName)
        viewModel.getUserDetailPhoto(userName)
        viewModel.getUserDetailLikes(userName)
        viewModel.getUserDetailCollections(userName)
    }

    val tabList = if (state.userDetailInfo?.totalCollections == 0
            && state.userDetailInfo.totalLikes == 0) {
        listOf("PHOTOS")
    } else if (state.userDetailInfo?.totalCollections == 0) {
        listOf(
            "PHOTOS",
            "LIKES",
        )
    } else if (state.userDetailInfo?.totalLikes == 0) {
        listOf(
            "PHOTOS",
            "COLLECTIONS"
        )
    } else {
        listOf(
            "PHOTOS",
            "LIKES",
            "COLLECTIONS"
        )
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                tabList.forEachIndexed { index, s ->
                    Tab(
                        text = {
                            Text(
                                text = tabList[index],
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
                count = tabList.size,
                state = pagerState,
                userScrollEnabled = false,
            ) {

            }
        }
    }
}


@Composable
private fun UserDetailInfo(userInfo: UserDetail?) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(125.dp, 125.dp)
                .padding(start = 16.dp)
                .clip(RoundedCornerShape(100)),
            model = userInfo?.profileImage?.large,
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Photos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = userInfo?.totalPhotos.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Likes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = userInfo?.totalLikes.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Collections",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = userInfo?.totalCollections.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}