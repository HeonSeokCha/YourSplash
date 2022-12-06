package com.chs.yoursplash.presentation.browse.collection_detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun CollectionDetailScreen(
    collectionId: String,
    navController: NavHostController,
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val lazyPagingItems = state.collectionPhotos?.collectAsLazyPagingItems()

    LaunchedEffect(context, viewModel) {
        viewModel.getCollectionDetail(collectionId)
        viewModel.getCollectionPhotos(collectionId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {

        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${state.collectionDetailInfo?.totalPhotos ?: 0} Photos ● " +
                        "Create by ${state.collectionDetailInfo?.user?.name ?: ""}",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        items(lazyPagingItems?.itemCount ?: 0) { idx ->
            ImageCard(
                photoInfo = lazyPagingItems?.get(idx),
                userClickAble = { userName ->
                    navController.navigate(
                        "${Screens.UserDetailScreen.route}/$userName"
                    )
                }, photoClickAble = { photoId ->
                    navController.navigate(
                        "${Screens.ImageDetailScreen.route}/$photoId"
                    )
                }
            )
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