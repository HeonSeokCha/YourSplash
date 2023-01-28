package com.chs.yoursplash.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.SearchFilter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchScreen(
    searchKeyWord: String,
    searchFilter: SearchFilter,
    modalClick: () -> Unit,
    onBack: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabList = listOf("PHOTOS", "COLLECTIONS", "USERS")

    DisposableEffect(Unit) {
        onDispose {
            onBack()
        }
    }
    LaunchedEffect(pagerState) {
        // Collect from the pager state a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Log.e("SEARCHSCREEN", page.toString())
        }
    }
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
                        type = Constants.SEARCH_PHOTO,
                        searchFilter = searchFilter,
                        modalClick = {
                            modalClick()
                        }
                    )
                }
                1 -> {
                    SearchResultScreen(
                        query = searchKeyWord,
                        type = Constants.SEARCH_COLLECTION,
                        modalClick = { }
                    )
                }
                2 -> {
                    SearchResultScreen(
                        query = searchKeyWord,
                        type = Constants.SEARCH_USER,
                        modalClick = { }
                    )
                }
            }
        }
    }
}