package com.chs.yoursplash.presentation.main.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        items(state.collectionList.size) { idx ->
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = state.collectionList[idx].previewPhotos?.get(0)?.urls?.small_s3,
                contentDescription = null
            )
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
    }
}