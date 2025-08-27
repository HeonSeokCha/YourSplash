package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchResultPhotoUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchFilter: SearchFilter): Flow<PagingData<Photo>> {
        return repository.getSearchResultPhoto(searchFilter)
    }
}