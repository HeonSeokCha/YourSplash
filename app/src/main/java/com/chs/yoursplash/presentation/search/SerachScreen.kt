package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.chs.yoursplash.util.Constants
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchScreen(
    searchKeyWord: String
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabList = listOf("PHOTOS", "COLLECTIONS","USERS")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
            count = tabList.size,
            state = pagerState,
            userScrollEnabled = false,
        ) {

            when (pagerState.currentPage) {
                0 -> {
                    SearchResultScreen(
                        query = searchKeyWord,
                        type = Constants.SEARCH_PHOTO
                    )
                }
                1 -> {
                    SearchResultScreen(
                        query = searchKeyWord,
                        type = Constants.SEARCH_COLLECTION
                    )
                }
                2 -> {
                    SearchResultScreen(
                        query = searchKeyWord,
                        type = Constants.SEARCH_USER
                    )
                }
            }
        }
    }
}