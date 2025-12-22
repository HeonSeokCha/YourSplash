package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.presentation.pxToDp
import kotlinx.coroutines.delay

@Composable
fun FlexibleCollapsingAppBar(
    modifier: Modifier = Modifier,
    expandedContent: @Composable (progress: Float, scrollState: ScrollState) -> Unit,
    collapsedContent: @Composable (progress: Float) -> Unit,
    stickyHeaderContent: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val expandedScrollState = rememberScrollState()
    val density = LocalDensity.current

    var measuredExpandedHeight by remember { mutableStateOf(200) }
    val expandedHeightDp = with(density) { measuredExpandedHeight.toDp() }
    var measuredStickyHeight by remember { mutableStateOf(150) }
    val stickyHeightDp = with(density) { measuredStickyHeight.toDp() }
    var globalHeight by remember { mutableStateOf(200) }

    val minHeight by remember { mutableStateOf(56.dp) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            var appBarScroll = 0f

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val maxScroll = if (measuredExpandedHeight > 0) {
                    with(density) { expandedHeightDp.toPx() } - with(density) { minHeight.toPx() }
                } else {
                    0f
                }

                if (appBarScroll >= 0f && delta > 0 && expandedScrollState.canScrollBackward) {
                    return Offset.Zero
                }

                val newScroll = (appBarScroll + delta).coerceIn(-maxScroll, 0f)
                val consumed = newScroll - appBarScroll
                appBarScroll = newScroll

                return Offset(0f, consumed)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                val maxScroll = if (measuredExpandedHeight > 0) {
                    with(density) { expandedHeightDp.toPx() } - with(density) { minHeight.toPx() }
                } else {
                    0f
                }

                if (delta < 0 && appBarScroll < 0f) {
                    val newScroll = (appBarScroll + delta).coerceIn(-maxScroll, 0f)
                    val consumed = newScroll - appBarScroll
                    appBarScroll = newScroll
                    return Offset(0f, consumed)
                }

                return Offset.Zero
            }
        }
    }

    val progress = remember {
        derivedStateOf {
            val maxScroll = if (measuredExpandedHeight > 0) {
                with(density) { expandedHeightDp.toPx() } - with(density) { minHeight.toPx() }
            } else {
                0f
            }

            if (maxScroll > 0) {
                (1f + (nestedScrollConnection.appBarScroll / maxScroll)).coerceIn(0f, 1f)
            } else {
                1f
            }
        }
    }

    val currentHeight = remember {
        derivedStateOf {
            if (measuredExpandedHeight > 0) {
                val height = with(density) { expandedHeightDp.toPx() } + nestedScrollConnection.appBarScroll
                with(density) {
                    height.toDp().coerceIn(minHeight, expandedHeightDp)
                }
            } else {
                minHeight
            }
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .padding(top = globalHeight.pxToDp())
        ) {
            content(PaddingValues(top = expandedHeightDp + stickyHeightDp))
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .fillMaxWidth()
//                    .height(currentHeight.value)
                    .onSizeChanged { size ->
                        globalHeight = size.height
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.pxToDp())
                        .onSizeChanged {
                            measuredExpandedHeight = it.height
                        }
                        .alpha(progress.value)
                ) {
                    expandedContent(0f, expandedScrollState)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(minHeight)
                        .padding(top = 26.dp)
                        .align(Alignment.TopCenter)
                        .alpha(1f - progress.value)
                ) {
                    collapsedContent(0f)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Top, unbounded = true)
                    .onSizeChanged {
                        measuredStickyHeight = it.height
                    }
            ) {
                stickyHeaderContent()
            }
        }
    }
}