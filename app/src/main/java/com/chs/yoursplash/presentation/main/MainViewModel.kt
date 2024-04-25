package com.chs.yoursplash.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chs.yoursplash.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.GetRecentSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.InsertSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getRecentSearchHistoryUseCase().collect { list ->
                _state.update { it.copy(searchHistory = list) }
            }
        }
    }


    fun insertSearchHistory(query: String) {
        viewModelScope.launch {
            insertSearchHistoryUseCase(query)
        }
    }


    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            deleteSearchHistoryUseCase(query)
        }
    }
}