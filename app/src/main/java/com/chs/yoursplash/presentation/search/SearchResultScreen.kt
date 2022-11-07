package com.chs.yoursplash.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.chs.yoursplash.util.Constants

@Composable
fun SearchResultScreen(
    query: String,
    type: String,
    viewModel: SearchResultViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    viewModel.searchPage = type

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        when (viewModel.searchPage) {
            Constants.SEARCH_PHOTO -> {
                pagingList?.let {
                    val photoList = it as LazyPagingItems<Photo>
                    items(photoList) { item ->
                        ImageCard(item)
                    }
                }
            }
            Constants.SEARCH_COLLECTION -> {
                pagingList?.let {
                    val photoList = it as LazyPagingItems<UnSplashCollection>
                    items(photoList) { item ->
                        CollectionCard(item)
                    }
                }
            }
            Constants.SEARCH_USER -> {
                pagingList?.let {
                    val photoList = it as LazyPagingItems<User>
                    items(photoList) { item ->
                        UserCard(item)
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
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

}