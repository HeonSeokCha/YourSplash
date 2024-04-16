package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.data.db.entity.PhotoSaveEntity
import com.chs.yoursplash.domain.repository.SplashRepository
import javax.inject.Inject

class InsertPhotoSaveInfoUseCase @Inject constructor(
    private val repository: SplashRepository
){
    suspend operator fun invoke(photoSaveEntity: PhotoSaveEntity): Long {
        return repository.insertSavePhotoInfo(photoSaveEntity)
    }
}