package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.model.UserDetail

@Composable
fun UserDetailScreen(
    userId: String,
    navController: NavHostController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}


@Composable
private fun UserDetailInfo(userInfo: UserDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
       AsyncImage(
           modifier = Modifier
               .size(200.dp, 200.dp),
           model = userInfo.profileImage.large,
           contentDescription = null
       )

        Row {
            Column {
                Text(
                    text = "Photos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = userInfo.totalPhotos.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}