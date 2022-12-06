package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.data.db.PhotoSaveInfo
import com.chs.yoursplash.domain.repository.SplashRepository
import javax.inject.Inject

class InsertPhotoSaveInfoUseCase @Inject constructor(
    private val repository: SplashRepository
){
    suspend operator fun invoke(photoSaveInfo: PhotoSaveInfo): Long {
        return repository.insertSavePhotoInfo(photoSaveInfo)
    }
}