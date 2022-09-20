package com.chs.yoursplash.presentation.image_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.chs.yoursplash.R

@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(context, viewModel) {
        viewModel.getImageDetailInfo("xIodDks1cQ8")
    }

   Column(
       modifier = Modifier
           .fillMaxSize()
   ) {
       AsyncImage(
           modifier = Modifier
               .fillMaxWidth()
               .height(300.dp)
           ,
           contentScale = ContentScale.Crop,
           model = state.imageDetailInfo?.urls?.full ?: "",
           placeholder = painterResource(R.drawable.test_user_profile_image),
           contentDescription = null
       )
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