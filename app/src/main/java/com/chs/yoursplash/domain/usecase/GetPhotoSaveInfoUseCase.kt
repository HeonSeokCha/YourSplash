package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.data.db.PhotoSaveInfo
import com.chs.yoursplash.domain.repository.SplashRepository
import javax.inject.Inject

class GetPhotoSaveInfoUseCase @Inject constructor(
    private val repository: SplashRepository
){
    suspend operator fun invoke(fileName: String): PhotoSaveInfo? {
        return repository.getSavePhoto(fileName)
    }
}