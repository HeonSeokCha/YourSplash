package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(searchHistory: String) {
    }
}
