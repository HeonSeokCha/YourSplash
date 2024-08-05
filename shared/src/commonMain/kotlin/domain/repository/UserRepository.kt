package domain.repository

import androidx.paging.PagingData
import domain.model.Photo
import com.chs.yoursplash.util.NetworkResult
import domain.model.UnSplashCollection
import domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserDetail(userName: String): Flow<NetworkResult<UserDetail>>

    fun getUserDetailPhotos(userName: String): Flow<PagingData<Photo>>

    fun getUserDetailLikePhotos(userName: String): Flow<PagingData<Photo>>

    fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>>
}