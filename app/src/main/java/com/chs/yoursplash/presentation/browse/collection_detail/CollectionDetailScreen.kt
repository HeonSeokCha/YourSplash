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

@Composable
fun CollectionDetailScreen(
    collectionId: String,
    navController: NavHostController,
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(context, viewModel) {
        viewModel.getCollectionDetail(collectionId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
    }
}