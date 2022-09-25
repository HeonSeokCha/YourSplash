package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    navController: NavHostController,
    photoInfo: UnSplashImage
) {

    Column (modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 4.dp,
                    bottom = 8.dp
                )
                .clickable {
                   navController.navigate(
                       "${Screens.UserDetailScreen.route}/${photoInfo.user.id}"
                   )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100)),
                model = photoInfo.user.photoProfile.large,
                placeholder = painterResource(com.chs.yoursplash.R.drawable.test_user_profile_image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = photoInfo.user.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    navController.navigate(
                        "${Screens.ImageDetailScreen.route}/${photoInfo.id}"
                    )
                }
                .background(color = photoInfo.color.color),
            model = photoInfo.urls.small,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(com.chs.yoursplash.R.drawable.test_photo),
            contentDescription = null
        )
    }
}