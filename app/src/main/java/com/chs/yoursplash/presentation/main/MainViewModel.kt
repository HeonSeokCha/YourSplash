package com.chs.yoursplash.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


    fun insertSearchHistory(query: String) {
        viewModelScope.launch {

        }
    }


    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {

        }
    }
}