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
import com.chs.yoursplash.domain.model.User
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserCard(
    userInfo: User?,
    userClickAble: (userName: String) -> Unit,
    photoClickAble: (photoId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    if (userInfo == null) return@clickable
                    userClickAble(userInfo.userName)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100))
                    .shimmer(visible = userInfo == null),
                url = userInfo?.profileImageUrl
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier.shimmer(userInfo == null),
                text = userInfo?.userName ?: stringResource(Res.string.lorem_ipsum),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (userInfo?.photos == null || userInfo.photos.isEmpty()) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(90.dp, 190.dp)
                            .clip(RoundedCornerShape(15))
                            .shimmer(true)
                    )
                }
            } else {
                userInfo.photos.forEach { info ->
                    ShimmerImage(
                        modifier = Modifier
                            .size(90.dp, 190.dp)
                            .clip(RoundedCornerShape(15))
                            .clickable { photoClickAble(info.id) },
                        url = info.urls
                    )
                }
            }
        }
    }
}