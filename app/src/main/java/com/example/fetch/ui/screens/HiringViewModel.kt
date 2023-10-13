package com.example.fetch.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.network.HiringApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HiringUiState {
    data class Success(val hiring: String) : HiringUiState
    object Error : HiringUiState
    object Loading : HiringUiState
}

class HiringViewModel : ViewModel() {
    var hiringUiState: HiringUiState by mutableStateOf(HiringUiState.Loading)
        private set

    init {
        getHiringData()
    }

    private fun getHiringData() {
        viewModelScope.launch {
            hiringUiState = try {
                val listResult = HiringApi.retrofitService.getHiring()
                HiringUiState.Success("Success: ${listResult.size} entries retrieved")
            } catch (e: IOException) {
                HiringUiState.Error
            }
        }
    }
}