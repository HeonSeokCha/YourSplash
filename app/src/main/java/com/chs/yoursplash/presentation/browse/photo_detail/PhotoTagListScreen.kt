package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun PhotoTagListScreen(
    tag: String,
    navController: NavHostController,
    viewModel: PhotoTagListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val resultPagingItems = viewModel.state.tagSearchResultList?.collectAsLazyPagingItems()

    LaunchedEffect(context, viewModel) {
        viewModel.getTagSearchResult(tag)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(resultPagingItems?.itemCount ?: 0) {idx ->
            ImageCard(
                photoInfo = resultPagingItems?.get(idx),
                loadQuality = state.loadQuality,
                userClickAble = { userName ->
                    navController.navigate(
                        "${Screens.UserDetailScreen}/$userName"
                    )
                }, photoClickAble = { photoId ->
                    navController.navigate(
                        "${Screens.ImageDetailScreen}/$photoId"
                    )
                }
            )
        }
    }
}