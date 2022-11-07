package com.chs.yoursplash.presentation.base

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants

@Composable
fun UserCard(userInfo: User?) {

    val context = LocalContext.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                context.startActivity(
                    Intent(context, BrowseActivity::class.java).apply {
                        putExtra(Constants.TARGET_TYPE, Constants.TARGET_USER)
                        putExtra(Constants.TARGET_ID, userInfo?.userName ?: "")
                    }
                )
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(100)),
            model = userInfo?.photoProfile?.large,
            contentDescription = null
        )

        Column {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = userInfo?.name ?: "",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(top = 8.dp, end = 8.dp)
            ) {
                items (userInfo?.photos?.size ?: 0) { idx ->
                    AsyncImage(
                        modifier = Modifier
                            .size(90.dp, 190.dp)
                            .clip(RoundedCornerShape(15))
                            .clickable {
                                context.startActivity(
                                    Intent(context, BrowseActivity::class.java).apply {
                                        putExtra(Constants.TARGET_TYPE, Constants.TARGET_PHOTO)
                                        putExtra(Constants.TARGET_ID, userInfo?.photos?.get(idx)?.id ?: "")
                                    }
                                )
                            },
                        model = userInfo?.photos?.get(idx)?.urls?.small_s3,
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            }
        }
    }
}