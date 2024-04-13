package com.chs.yoursplash.presentation.search

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.base.CollectionCard
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.UserCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.SearchFilter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchResultScreen(
    query: String,
    searchFilter: SearchFilter? = null,
    type: String,
    modalClick: () -> Unit,
    viewModel: SearchResultViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    LaunchedEffect(context, viewModel) {
        viewModel.searchPage = type
    }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            viewModel.searchResult(query)
        }
    }

    LaunchedEffect(searchFilter) {
        if (searchFilter != null && searchFilter != SearchFilter()) {
            viewModel.orderBy = searchFilter.orderBy
            viewModel.color = searchFilter.color
            viewModel.orientation = searchFilter.orientation
            viewModel.searchResult(query)
        }
    }


    val pagingList = when (type) {
        Constants.SEARCH_PHOTO -> {
            state.searchPhotoList?.collectAsLazyPagingItems()
        }
        Constants.SEARCH_COLLECTION -> {
            state.searchCollectionList?.collectAsLazyPagingItems()
        }
        Constants.SEARCH_USER -> {
            state.searchUserList?.collectAsLazyPagingItems()
        }
        else -> {
            state.searchPhotoList?.collectAsLazyPagingItems()
        }
    }

    Scaffold(
        floatingActionButton = {
            if (type == Constants.SEARCH_PHOTO && query.isNotEmpty()) {
                SearchFloatingActionButton(extend = scrollState.isScrollingUp()) {
                    modalClick()
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            when (viewModel.searchPage) {
                Constants.SEARCH_PHOTO -> {
                    pagingList?.let {
                        val photoList = it as LazyPagingItems<Photo>
                        items(
                            count = photoList.itemCount
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
                    pagingList?.let {
                        val photoList = it as LazyPagingItems<UnSplashCollection>
                        items(
                            count = photoList.itemCount
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
                    pagingList?.let {
                        val photoList = it as LazyPagingItems<User>
                        items(
                            count = photoList.itemCount
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


        when (pagingList?.loadState?.source?.refresh) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {}
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