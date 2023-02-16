package com.chs.yoursplash.presentation.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.SearchFilter
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
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
            pageCount = tabList.size,
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