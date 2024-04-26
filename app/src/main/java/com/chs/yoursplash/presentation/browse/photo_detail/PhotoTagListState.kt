package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.annotation.concurrent.Immutable

@Immutable
data class PhotoTagListState(
    val tagSearchResultList: Flow<PagingData<Photo>>? = null,
    val loadQuality: String = "regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
