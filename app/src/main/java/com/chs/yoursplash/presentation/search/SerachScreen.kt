package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    onBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val tabList = remember { listOf("PHOTOS", "COLLECTIONS", "USERS") }
    val pagerState = rememberPagerState(initialPage = 0) { tabList.size }

    DisposableEffect(Unit) {
        onDispose {
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
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
                            fontSize = 12.sp
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

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    val viewModel: SearchResultViewModel = hiltViewModel<SearchResultViewModel>(key = Constants.SEARCH_PHOTO).apply {
                        initSearchType(Constants.SEARCH_PHOTO)
                    }
                    SearchResultScreen(
                        state = viewModel.state,
                        onSearch = {},
                        modalClick = { }
                    )
                }
                1 -> {
                    val viewModel: SearchResultViewModel = hiltViewModel(key = Constants.SEARCH_COLLECTION)
                    SearchResultScreen(
                        state = viewModel.state,
                        onSearch = {},
                        modalClick = { }
                    )
                }
                2 -> {
                    val viewModel: SearchResultViewModel = hiltViewModel(key = Constants.SEARCH_USER)
                    SearchResultScreen(
                        state = viewModel.state,
                        onSearch = {},
                        modalClick = { }
                    )
                }
            }
        }
    }
}