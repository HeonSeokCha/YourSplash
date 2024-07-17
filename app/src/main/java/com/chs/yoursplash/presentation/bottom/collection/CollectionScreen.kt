package com.chs.yoursplash.presentation.bottom.collection

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.presentation.base.CollectionInfoCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants

@Composable
fun CollectionScreen(
    state: CollectionState,
    onEvent: () -> Unit
) {
    val context = LocalContext.current

    val lazyPagingItems = state.collectionList?.collectAsLazyPagingItems()

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
                key = lazyPagingItems.itemKey { it.id }
            ) { idx ->
                val collectionInfo = lazyPagingItems[idx]
                CollectionInfoCard(
                    collectionInfo = collectionInfo,
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
                                putExtra(Constants.TARGET_TYPE, Constants.TARGET_COLLECTION)
                                putExtra(Constants.TARGET_ID, collectionId)
                            }
                        )
                    }
                )
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}