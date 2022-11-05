package com.chs.yoursplash.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultPhotoUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    operator fun invoke(
        query: String,
        orderBy: String,
        color: String?,
        orientation: String?
    ): Flow<PagingData<Photo>> {
        Log.e("searchResult", query)
        return repository.getSearchResultPhoto(
            query = query,
            orderBy = orderBy,
            color = color,
            orientation = orientation
        )
    }
}