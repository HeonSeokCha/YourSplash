package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CollectionCard(

) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        // TODO: navigate userActivity.
                    },
                model = "",
                contentDescription = null
            )
            Text(
                text = "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        // TODO: navigate imageDetailActivity.
                    },
                model = "",
                contentDescription = null
            )
            Box(modifier = Modifier
                .padding(
                    start = 16.dp,
                    bottom = 16.dp
                ),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = "")
                Text(text = "")
            }
        }
    }
}