package com.chs.yoursplash.presentation.bottom.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetFavoriteImageListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class FavoriteViewModel(
    private val getFavoriteImageListUseCase: GetFavoriteImageListUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<FavoriteState> = MutableStateFlow(FavoriteState())
    val state = _state
        .onStart { getImageList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )
    private val _effect: Channel<FavoriteEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: FavoriteIntent) {
        when (intent) {
           is FavoriteIntent.ClickPhoto -> {
               _effect.trySend(FavoriteEffect.NavigatePhotoDetail(intent.id))
           }
        }
    }

    private fun getImageList() {
        viewModelScope.launch {
            getFavoriteImageListUseCase().collect { favoriteList ->
                _state.update { it.copy(favoriteList = favoriteList) }
            }
        }
    }
}