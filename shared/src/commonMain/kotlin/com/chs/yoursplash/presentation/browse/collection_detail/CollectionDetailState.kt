package com.chs.yoursplash.presentation.browse.collection_detail

import com.chs.yoursplash.domain.model.UnSplashCollection

data class CollectionDetailState(
    val collectionDetailInfo: UnSplashCollection? = null,
    val isDetailLoad: Boolean = false,
    val isPagingLoading: Boolean = false
)