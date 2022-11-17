package com.chs.yoursplash.presentation.main.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lazyPagingItems = state.pagingImageList?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        items(lazyPagingItems!!) { photo ->
            ImageCard(
                photoInfo = photo,
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

    when (lazyPagingItems?.loadState?.source?.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
        }
        is LoadState.Error -> {
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}