package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.lorem_ipsum
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageCard(
    photoInfo: Photo?,
    onPhotoClick: (String) -> Unit = {},
    onUserClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 4.dp,
                    bottom = 8.dp
                )
                .clickable {
                    if (photoInfo?.user?.userName != null) {
                        onUserClick(photoInfo.user.userName)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100))
                    .shimmer(photoInfo == null),
                url = photoInfo?.user?.profileImageUrl
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier.shimmer(photoInfo == null),
                text = photoInfo?.user?.name ?: stringResource(Res.string.lorem_ipsum),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        ShimmerImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio((photoInfo?.width ?: 16).toFloat() / (photoInfo?.height ?: 9).toFloat())
                .clip(RoundedCornerShape(10.dp))
                .shimmer(photoInfo == null)
                .clickable {
                    if (photoInfo?.id == null) return@clickable
                    onPhotoClick(photoInfo.id)
                },
            url = photoInfo?.urls
        )
    }
}