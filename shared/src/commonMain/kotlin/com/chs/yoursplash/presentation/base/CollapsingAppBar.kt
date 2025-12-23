package com.chs.yoursplash.presentation.base

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.chs.yoursplash.presentation.pxToDp


internal class BackgroundScrollConnection(
    private val scrollState: ScrollState
) : NestedScrollConnection {

    private var isHeaderHide: Boolean = false

    fun changeHeaderValue(value: Boolean) {
        isHeaderHide = value
    }

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val dy = available.y

        return when {
            isHeaderHide -> {
                Offset.Zero
            }

            dy < 0 -> {
                scrollState.dispatchRawDelta(dy * -1)
                Offset(0f, dy)
            }

            else -> {
                Offset.Zero
            }
        }
    }
}

@Composable
fun CollapsingToolbarScaffold(
    scrollState: ScrollState,
    header: @Composable () -> Unit,
    isShowTopBar: Boolean,
    topBarItem: ImageVector? = null,
    onIconClick: () -> Unit = {},
    onCloseClick: () -> Unit,
    stickyHeader: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    val nestedScrollConnection = remember {
        BackgroundScrollConnection(scrollState)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var globalHeight by remember { mutableIntStateOf(0) }
        var visiblePercentage by remember { mutableFloatStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (isShowTopBar) 88.dp else 0.dp)
                .onSizeChanged { size ->
                    globalHeight = size.height
                }
                .verticalScroll(scrollState)
                .nestedScroll(nestedScrollConnection)
        ) {
            Column {
                HeadSection(
                    scrollState = scrollState,
                    header = header,
                    onVisibleChange = { visiblePercentage = it },
                    onHide = { isHide ->
                        nestedScrollConnection.changeHeaderValue(isHide)
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.pxToDp())
                ) {
                    stickyHeader()
                    content()
                }
            }
        }

        if (isShowTopBar) {
            GradientTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .background(MaterialTheme.colorScheme.primary),
                topBarIcon = topBarItem,
                onIconClick = onIconClick,
                onCloseClick = onCloseClick
            )
        } else {
            GradientTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(1f - visiblePercentage)
                    .align(Alignment.TopStart)
                    .background(MaterialTheme.colorScheme.primary),
                onCloseClick = {
                    if (visiblePercentage > 0.5f) return@GradientTopBar
                    onCloseClick()
                }
            )
        }
    }
}

@Composable
fun CollapsingToolbarScaffold2(
    scrollState: ScrollState,
    expandContent: @Composable (alpha: Float) -> Unit,
    collapsedContent: @Composable (alpha: Float) -> Unit,
    stickyContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    val nestedScrollConnection = remember {
        BackgroundScrollConnection(scrollState)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var globalHeight by remember { mutableIntStateOf(0) }
        var visiblePercentage by remember { mutableFloatStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    globalHeight = size.height
                }
                .verticalScroll(scrollState)
                .nestedScroll(nestedScrollConnection)
        ) {
            Column {
                HeadSection(
                    scrollState = scrollState,
                    header = { expandContent(visiblePercentage) },
                    onHide = { isHide ->
                        nestedScrollConnection.changeHeaderValue(isHide)
                    }, onVisibleChange = { visiblePercentage = it }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.pxToDp())
                ) {
                    stickyContent()
                    content()
                }
            }
        }

        collapsedContent(visiblePercentage)

//        if (isShowTopBar) {
//            GradientTopBar(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopStart)
//                    .background(MaterialTheme.colorScheme.primary),
//                topBarIcon = topBarItem,
//                onIconClick = onIconClick,
//                onCloseClick = onCloseClick
//            )
//        } else {
//            GradientTopBar(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .alpha(1f - visiblePercentage)
//                    .align(Alignment.TopStart)
//                    .background(MaterialTheme.colorScheme.primary),
//                onCloseClick = {
//                    if (visiblePercentage > 0.5f) return@GradientTopBar
//                    onCloseClick()
//                }
//            )
//        }
    }
}

@Composable
fun GradientTopBar(
    modifier: Modifier,
    topBarIcon: ImageVector? = null,
    onIconClick: () -> Unit = {},
    onCloseClick: () -> Unit
) {
    Box {
        Box(
            modifier = modifier
                .padding(top = 32.dp)
                .height(56.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.TopStart),
                onClick = onCloseClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = White,
                    contentDescription = null
                )
            }

            if (topBarIcon != null) {
                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.TopEnd),
                    onClick = onIconClick
                ) {
                    Icon(
                        imageVector = topBarIcon,
                        tint = White,
                        contentDescription = null
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.Transparent)
        )
    }
}

@Composable
private fun HeadSection(
    scrollState: ScrollState,
    header: @Composable () -> Unit,
    onHide: (Boolean) -> Unit,
    onVisibleChange: (Float) -> Unit
) {
    var contentHeight by remember { mutableIntStateOf(0) }
    var visiblePercentage by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(scrollState.value, contentHeight) {
        if (contentHeight > 0) {
            // 스크롤 양에 따라 가시성 계산
            visiblePercentage = ((contentHeight - scrollState.value).toFloat() / contentHeight)
                .coerceIn(0f, 1f)

            onVisibleChange(visiblePercentage)
            onHide(visiblePercentage <= 0f)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 1.dp)
            .onSizeChanged {
                contentHeight = it.height
            }
            .alpha(visiblePercentage)
    ) {
        header()
    }
}
