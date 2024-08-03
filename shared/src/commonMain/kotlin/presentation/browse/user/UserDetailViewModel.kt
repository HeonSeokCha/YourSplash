package presentation.browse.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import util.Constants
import com.chs.yoursplash.util.NetworkResult
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.GetUserCollectionUseCase
import domain.usecase.GetUserDetailUseCase
import domain.usecase.GetUserLikesUseCase
import domain.usecase.GetUserPhotoUseCase
import kotlinx.coroutines.launch
import presentation.browse.user.UserDetailState

class UserDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserPhotoUseCase: GetUserPhotoUseCase,
    private val getUserLikesUseCase: GetUserLikesUseCase,
    private val getUserCollectionUseCase: GetUserCollectionUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private val userName: String = savedStateHandle[Constants.ARG_KEY_USER_NAME] ?: ""

    var state by mutableStateOf(UserDetailState())
        private set

    init {
        viewModelScope.launch {
            state = UserDetailState(
                loadQuality = getLoadQualityUseCase(),
                userDetailPhotoList = getUserPhotoUseCase(userName).cachedIn(viewModelScope),
                userDetailCollection = getUserCollectionUseCase(userName).cachedIn(viewModelScope),
                userDetailLikeList = getUserLikesUseCase(userName).cachedIn(viewModelScope)
            )
        }
    }

    fun getUserDetailInfo() {
        viewModelScope.launch {
            getUserDetailUseCase(userName).collect { result ->
                state = when (result) {
                    is NetworkResult.Loading -> {
                        state.copy(
                            isLoading = true,
                            isError = false
                        )
                    }

                    is NetworkResult.Success -> {
                        state.copy(
                            isLoading = false,
                            userDetailInfo = result.data
                        )
                    }

                    is NetworkResult.Error -> {
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
}