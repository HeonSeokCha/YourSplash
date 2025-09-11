package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetUserCollectionUseCase(
    private val repository: UserRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(userName: String): Flow<PagingData<UnSplashCollection>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getUserDetailCollections(userName = userName, loadQuality = it)
    }
}