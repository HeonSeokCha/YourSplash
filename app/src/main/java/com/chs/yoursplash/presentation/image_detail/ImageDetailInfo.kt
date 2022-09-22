package com.chs.yoursplash.presentation.image_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.domain.model.UnSplashImageDetail

@Composable
fun ImageDetailInfo(
    imageDetailInfo: UnSplashImageDetail?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoText(title = "Camera", value = imageDetailInfo?.exif?.model ?: "Unknown")
            InfoText(title = "Aperture", value = imageDetailInfo?.exif?.aperture ?: "Unknown")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoText(title = "Focal Length", value = imageDetailInfo?.exif?.focalLength ?: "Unknown")
            InfoText(title = "Shutter Speed", value = imageDetailInfo?.exif?.exposureTime ?: "Unknown")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoText(title = "ISO", value = imageDetailInfo?.exif?.iso?.toString() ?: "Unknown")
            InfoText(title = "Dimensions", value = "${imageDetailInfo?.width} X ${imageDetailInfo?.height}")
        }
    }
}


@Composable
fun InfoText(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = value
        )
    }
}