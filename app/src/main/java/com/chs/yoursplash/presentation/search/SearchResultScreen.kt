package com.chs.yoursplash.presentation.search

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.base.CollectionCard
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.UserCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.SearchFilter
import kotlinx.coroutines.launch

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
    val lazyListState = rememberLazyListState()
    viewModel.searchPage = type

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            viewModel.searchResult(query)
        }
    }

    LaunchedEffect(searchFilter) {
        if (searchFilter != null && searchFilter != SearchFilter()) {
            viewModel.orderBy = Constants.SORT_BY_LIST[searchFilter.orderBy]!!
            viewModel.color = Constants.SEARCH_COLOR_LIST[searchFilter.color]
            viewModel.orientation = Constants.SEARCH_ORI_LIST[searchFilter.orientation]
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
                SearchFloatingActionButton(extend = lazyListState.isScrollingUp()) {
                    Log.e("FABCLICK", "0")
                    modalClick()
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            when (viewModel.searchPage) {
                Constants.SEARCH_PHOTO -> {
                    pagingList?.let {
                        val photoList = it as LazyPagingItems<Photo>
                        items(photoList) { item ->
                            ImageCard(
                                photoInfo = item,
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
                        items(photoList) { item ->
                            CollectionCard(
                                collectionInfo = item,
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
                        items(photoList) { item ->
                            UserCard(
                                userInfo = item,
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
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Filter,
                contentDescription = null
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