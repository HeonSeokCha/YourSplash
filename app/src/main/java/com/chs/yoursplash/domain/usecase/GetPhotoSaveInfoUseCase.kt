package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.data.db.entity.PhotoSaveEntity
import com.chs.yoursplash.domain.repository.SplashRepository
import javax.inject.Inject

class GetPhotoSaveInfoUseCase @Inject constructor(
    private val repository: SplashRepository
){
    suspend operator fun invoke(fileName: String): PhotoSaveEntity? {
        return repository.getSavePhoto(fileName)
    }
}