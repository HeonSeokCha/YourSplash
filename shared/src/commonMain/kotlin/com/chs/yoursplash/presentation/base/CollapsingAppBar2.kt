package com.chs.yoursplash.presentation.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarScaffold() {

    val expandedAppBarHeight = 180.dp
    val headerTranslation = (expandedAppBarHeight / 2)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = scrollBehavior.state
    val appBarExpanded by remember {
        derivedStateOf { scrollState.collapsedFraction < 0.9f }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .zIndex(0f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                CollapsedAppBar(visible = !appBarExpanded)
                TopAppBar(
                    title = {
                        AppBarHeader(
                            modifier = Modifier.graphicsLayer {
                                translationY =
                                    scrollState.collapsedFraction * headerTranslation.toPx()
                            },
                            visible = appBarExpanded
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    expandedHeight = expandedAppBarHeight,
                    windowInsets = WindowInsets(0),
                    scrollBehavior = scrollBehavior
                )
            }
        },
    ) { innerPadding ->
        val cornerRadius = 16.dp
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val shadowColor = Color.Black.copy(alpha = 0.25f)
                    }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = cornerRadius
                        )
                    )
            ) {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedAppBar(
    modifier: Modifier = Modifier,
    visible: Boolean
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween()),
                exit = fadeOut(animationSpec = tween())
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "John Doe",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            } }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        )
    )
}

@Composable
fun AppBarHeader(
    modifier: Modifier = Modifier,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween()),
        exit = fadeOut(animationSpec = tween())
    ) {
        Column(
            modifier = modifier.padding(end = 16.dp, top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "John Doe",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "@johndoe",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text(
                text = "",
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}