package com.chs.yoursplash.presentation.bottom.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants


@Composable
fun HomeScreen(state: HomeState) {
    val context = LocalContext.current
    val lazyPagingItems = state.pagingImageList?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        if (lazyPagingItems != null && lazyPagingItems.itemCount != 0) {
            items(
                count = lazyPagingItems.itemCount,
                key = { lazyPagingItems[it]!!.id }
            ) {idx ->
                val photo = lazyPagingItems[idx]
                ImageCard(
                    photoInfo = photo,
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

    when (lazyPagingItems?.loadState?.source?.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is LoadState.Error -> {
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}