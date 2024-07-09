package com.chs.yoursplash.presentation.search

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.base.CollectionCard
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.UserCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.SearchFilter
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot

@Composable
fun SearchResultScreen(
    state: SearchState,
    onSearch: () -> Unit,
    modalClick: () -> Unit = { },
) {

    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    var placeItemShow by remember { mutableStateOf(false) }
    var isEmptyShow by remember { mutableStateOf(false) }


    LaunchedEffect(state.searchQuery) {
        snapshotFlow { state.searchQuery }
            .distinctUntilChanged()
            .filterNot{ it.isNullOrEmpty() }
            .collect {
                onSearch()
                scrollState.scrollToItem(0, 0)
            }
    }

    val pagingList = when (state.searchType) {
        Constants.SEARCH_PHOTO -> {
            state.searchPhotoList?.collectAsLazyPagingItems()
        }

        Constants.SEARCH_COLLECTION -> {
            state.searchCollectionList?.collectAsLazyPagingItems()
        }

        else -> {
            state.searchUserList?.collectAsLazyPagingItems()
        }
    }

    Scaffold(
//        floatingActionButton = {
//            if (type == Constants.SEARCH_PHOTO && query.isNotEmpty()) {
//                SearchFloatingActionButton(extend = scrollState.isScrollingUp()) {
//                    modalClick()
//                }
//            }
//        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            when (state.searchType) {
                Constants.SEARCH_PHOTO -> {
                    val photoList = pagingList as LazyPagingItems<Photo>?
                    if (photoList != null) {
                        items(
                            count = photoList.itemCount,
                            key = photoList.itemKey(key = { it.id })
                        ) { idx ->
                            val item = photoList[idx]
                            ImageCard(
                                photoInfo = item,
                                loadQuality = state.loadQuality,
                                userClickAble = { userName ->
                                    context.startActivity(
                                        Intent(context, BrowseActivity::class.java).apply {
                                            putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                                            putExtra(Constants.TARGET_ID, userName)
                                        }
                                    )
                                }, photoClickAble = { photoId ->
                                    context.startActivity(
                                        Intent(context, BrowseActivity::class.java).apply {
                                            putExtra(Constants.TARGET_TYPE, Constants.TARGET_PHOTO)
                                            putExtra(Constants.TARGET_ID, photoId)
                                        }
                                    )
                                }
                            )
                        }
                    }

                }

                Constants.SEARCH_COLLECTION -> {
                    val photoList = pagingList as LazyPagingItems<UnSplashCollection>?
                    if (photoList != null) {
                        items(
                            count = photoList.itemCount,
                            key = photoList.itemKey(key = { it.id })
                        ) { idx ->
                            val item = photoList[idx]
                            CollectionCard(
                                collectionInfo = item,
                                loadQuality = state.loadQuality,
                                userClickAble = { userName ->
                                    context.startActivity(
                                        Intent(context, BrowseActivity::class.java).apply {
                                            putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                                            putExtra(Constants.TARGET_ID, userName)
                                        }
                                    )
                                }, collectionClickAble = { collectionId ->
                                    context.startActivity(
                                        Intent(context, BrowseActivity::class.java).apply {
                                            putExtra(
                                                Constants.TARGET_TYPE,
                                                Constants.TARGET_COLLECTION
                                            )
                                            putExtra(Constants.TARGET_ID, collectionId)
                                        }
                                    )
                                }
                            )
                        }
                    }

                }

                Constants.SEARCH_USER -> {
                    val photoList = pagingList as LazyPagingItems<User>?
                    if (photoList != null) {
                        items(
                            count = photoList.itemCount,
                            key = photoList.itemKey(key = { it.id })
                        ) { idx ->
                            val item = photoList[idx]
                            UserCard(
                                userInfo = item,
                                loadQuality = state.loadQuality,
                                userClickAble = { userName ->
                                    Intent(context, BrowseActivity::class.java).apply {
                                        putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                                        putExtra(Constants.TARGET_ID, userName)
                                    }
                                }, photoClickAble = { photoId ->
                                    context.startActivity(
                                        Intent(context, BrowseActivity::class.java).apply {
                                            putExtra(Constants.TARGET_TYPE, Constants.TARGET_PHOTO)
                                            putExtra(Constants.TARGET_ID, photoId)
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }



        if (pagingList != null) {
            placeItemShow = when (pagingList.loadState.source.refresh) {
                is LoadState.Loading -> {
                    true
                }

                is LoadState.Error -> {
                    Toast.makeText(
                        context,
                        "An error occurred while loading...",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    false
                }

                else -> {
                    isEmptyShow = pagingList.itemCount == 0
                    pagingList.itemCount < 0
                }
            }
        }
    }
}

@Composable
fun SearchFloatingActionButton(
    extend: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null,
            )

            AnimatedVisibility(extend) {
                Text(
                    text = "FILTER",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}