package com.chs.yoursplash.presentation.browse.collection_detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun CollectionDetailScreen(
    state: CollectionDetailState,
    navController: NavHostController,
) {

    val context = LocalContext.current
    val lazyPagingItems = state.collectionPhotos?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {

        if (state.collectionDetailInfo != null) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${state.collectionDetailInfo.totalPhotos} Photos ● " +
                            "Create by ${state.collectionDetailInfo.user.name}",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }


        if (lazyPagingItems != null && lazyPagingItems.itemCount != 0) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.user.id }
            ) { idx ->
                ImageCard(
                    photoInfo = lazyPagingItems[idx],
                    loadQuality = state.loadQuality,
                    userClickAble = { userName -> navController.navigate(Screens.UserDetailScreen(userName)
                        )
                    }, photoClickAble = { photoId ->
                        navController.navigate(Screens.ImageDetailScreen(photoId))
                    }
                )
            }
        }
    }

    when (lazyPagingItems?.loadState?.source?.refresh) {
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