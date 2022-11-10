package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.yoursplash.domain.model.UnSplashCollection
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
    val  lazyPagingItems = viewModel.getCollectionPhotos(collectionId).collectAsLazyPagingItems()

    LaunchedEffect(context, viewModel) {
        viewModel.getCollectionDetail(collectionId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(lazyPagingItems) { photoInfo ->
            ImageCard(
                photoInfo = photoInfo,
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
}

@Composable
private fun CollectionDetailInfo(
    collectionInfo: UnSplashCollection,
    navController: NavHostController
) {
    
}