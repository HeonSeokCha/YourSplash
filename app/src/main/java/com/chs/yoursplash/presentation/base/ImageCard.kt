package com.chs.yoursplash.presentation.base

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.model.UnSplashImageUrls
import com.chs.yoursplash.domain.model.UnSplashUser
import com.chs.yoursplash.domain.model.UnsplashUserProfileImage
import com.chs.yoursplash.presentation.image_detail.ImageDetailActivity
import com.chs.yoursplash.presentation.user.UserDetailActivity
import com.chs.yoursplash.util.Constants.PHOTO_ID
import com.chs.yoursplash.util.color

@Composable
fun ImageCard(
    photoInfo: UnSplashImage
) {
    val context = LocalContext.current

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
                    context.startActivity(
                        Intent(context, UserDetailActivity::class.java)
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
                    context.startActivity(
                        Intent(context, ImageDetailActivity::class.java).apply {
                            putExtra(PHOTO_ID, photoInfo.id)
                        }
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

@Preview
@Composable
fun PreviewImageCard() {
    ImageCard(
        UnSplashImage(
            id = "",
            color = "#333333",
            width = 480,
            height = 270,
            urls = UnSplashImageUrls(
                raw = "",
                full = "",
                small = "",
                thumb = "",
                small_s3 = ""
            ),
            user = UnSplashUser(
                id = "",
                userName = "",
                name = "Mailchimp",
                photoProfile = UnsplashUserProfileImage(
                    small = "",
                    medium = "",
                    large = ""
                )
            )
        )
    )
}