package com.example.fetch.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.model.HiringData
import com.example.fetch.network.HiringApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HiringUiState {
    //receives a map of lists grouped by the listId
    data class Success(val hiring: Map<Int, List<HiringData>>) : HiringUiState
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
                var filteredResult = listResult
                    .filterNot { it.name == "" }
                    .sortedBy { it.id }
                    .groupBy { it.listId }
                    .toSortedMap(compareBy<Int> { it })
                HiringUiState.Success(filteredResult)
            } catch (e: IOException) {
                HiringUiState.Error
            } catch (e: retrofit2.HttpException) {
                HiringUiState.Error
            }
        }
    }
}