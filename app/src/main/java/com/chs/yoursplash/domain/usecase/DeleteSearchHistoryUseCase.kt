package com.chs.yoursplash.domain.usecase

import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    suspend operator fun invoke(searchHistory: String) {
    }
}