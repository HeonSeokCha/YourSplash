package com.chs.yoursplash.presentation.bottom.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import io.ktor.events.Events
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CollectionState())
    val state = _state.onStart {
        getCollection()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun changeEvent(events: CollectionEvent) {
        when (events) {
            CollectionEvent.OnRefresh -> {
                if (!_state.value.isRefresh) {
                    getCollection()
                }
                _state.update { it.copy(isRefresh = !it.isRefresh) }
            }

            else -> Unit
        }
    }

    private fun getCollection() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefresh = false,
                    isLoading = false,
                    collectionList = getHomeCollectionsUseCase().cachedIn(viewModelScope)
                )
            }
        }
    }
}