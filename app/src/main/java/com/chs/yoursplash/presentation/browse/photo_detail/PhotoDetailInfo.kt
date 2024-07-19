package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.UnSplashTag
import com.chs.yoursplash.util.color

@Composable
fun ImageDetailInfo(
    imageDetailInfo: PhotoDetail,
    tagClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            ItemDetailValue(title = "Views", value = imageDetailInfo.views)
            ItemDetailValue(title = "Downloads", value = imageDetailInfo.downloads)
            ItemDetailValue(title = "Likes", value = imageDetailInfo.likes)
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

        if (imageDetailInfo.tags.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Related tags",
                )

                Spacer(modifier = Modifier.height(8.dp))

                RelatedTags(
                    list = imageDetailInfo.tags,
                    color = imageDetailInfo.color
                ) {
                    tagClick(it)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ItemDetailValue(
    title: String,
    value: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value.toString(),
            fontWeight = FontWeight.Light
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RelatedTags(
    list: List<UnSplashTag>,
    color: String,
    onClick: (String) -> Unit
) {
    var maxLines by remember { mutableIntStateOf(1) }

    ContextualFlowRow(
        modifier = Modifier
            .animateContentSize(),
        itemCount = list.count(),
        maxLines = maxLines,
        overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
            minRowsToShowCollapse = 2,
            expandIndicator = {
                TextButton(
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            end = 4.dp
                        ),
                    onClick = { maxLines += 1 },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.surfaceVariant,
                        containerColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = "${this@expandOrCollapseIndicator.totalItemCount - this@expandOrCollapseIndicator.shownItemCount}+ more"
                    )
                }
            },
            collapseIndicator = {
                TextButton(
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            end = 4.dp
                        ),
                    onClick = { maxLines = 1 },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.errorContainer,
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                    Text(text = "Hide")
                }
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { idx ->
        val tag = list[idx]
        AssistChip(
            onClick = {
                onClick(tag.title)
            }, label = {
                Text(text = tag.title)
            }, colors = AssistChipDefaults.assistChipColors(
                containerColor = color.color,
                labelColor = Color.White
            ), border = AssistChipDefaults.assistChipBorder(
                enabled = true,
                borderColor = color.color,
            )
        )
    }
}
