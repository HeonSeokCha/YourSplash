package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val resultPagingItems = state.tagSearchResultList?.collectAsLazyPagingItems()

    LaunchedEffect(context, viewModel) {
        viewModel.getTagSearchResult(tag)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(resultPagingItems?.itemCount ?: 0) {idx ->
            ImageCard(
                photoInfo = resultPagingItems?.get(idx),
                loadQuality = state.loadQuality,
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