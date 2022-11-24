package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SplashRepository
import javax.inject.Inject

class DeletePhotoSaveInfoUseCase @Inject constructor(
    private val repository: SplashRepository
){
    suspend operator fun invoke(fileName: String): Int {
        return repository.deleteSavePhotoInfo(fileName)
    }
}