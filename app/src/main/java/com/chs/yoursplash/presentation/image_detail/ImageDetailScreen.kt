package com.chs.yoursplash.presentation.image_detail

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.user.UserDetailActivity

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
                .height(300.dp),
            contentScale = ContentScale.Crop,
            model = state.imageDetailInfo?.urls?.full ?: "",
            placeholder = painterResource(R.drawable.test_user_profile_image),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clickable {
                            context.startActivity(
                                Intent(context, UserDetailActivity::class.java)
                            )
                        }
                        .size(40.dp)
                        .clip(RoundedCornerShape(100)),
                    model = state.imageDetailInfo?.user?.photoProfile?.large,
                    placeholder = painterResource(R.drawable.test_user_profile_image),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = state.imageDetailInfo?.user?.name ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
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