package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import domain.model.Photo
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultPhotoUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(
        query: String,
        orderBy: String,
        color: String?,
        orientation: String?
    ): Flow<PagingData<Photo>> {
        return repository.getSearchResultPhoto(
            query = query,
            orderBy = orderBy,
            color = color,
            orientation = orientation
        )
    }
}