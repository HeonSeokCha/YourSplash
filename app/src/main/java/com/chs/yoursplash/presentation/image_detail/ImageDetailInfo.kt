package com.chs.yoursplash.presentation.image_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.domain.model.UnSplashImageDetail

@Composable
fun ImageDetailInfo(
    imageDetailInfo: UnSplashImageDetail?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Views",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = imageDetailInfo?.views.toString(),
                        fontWeight = FontWeight.Light
                    )
                }

                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Downloads",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = imageDetailInfo?.downloads.toString(),
                        fontWeight = FontWeight.Light
                    )
                }

                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Views",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = imageDetailInfo?.likes.toString(),
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
        }
    }
}
