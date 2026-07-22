package com.chs.yoursplash.presentation.bottom.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun FavoriteScreenRoot(
    viewModel: FavoriteViewModel,
    onBrowse: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FavoriteEffect.NavigatePhotoDetail -> {
                    onBrowse(BrowseInfo.Photo(effect.id))
                }
            }
        }
    }

    FavoriteScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun FavoriteScreen(
    state: FavoriteState,
    onIntent: (FavoriteIntent) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        when {
            state.isLoading -> {
                items(Constants.COUNT_LOADING_ITEM) {
                    ImageCard(null, isShowUserInfo = false)
                }
            }

            state.favoriteList.isEmpty() -> {
                item(span = StaggeredGridItemSpan.FullLine) {
                    ItemEmpty(
                        text = stringResource(Res.string.text_no_photos)
                    )
                }
            }

            else -> {
                items(
                    count = state.favoriteList.count(),
                    key = { state.favoriteList[it].id }
                ) { idx ->
                    val favoritePhotoInfo = state.favoriteList[idx]
                    ShimmerImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onIntent(FavoriteIntent.ClickPhoto(favoritePhotoInfo.id)) },
                        url = favoritePhotoInfo.url
                    )
                }
            }
        }
    }
}