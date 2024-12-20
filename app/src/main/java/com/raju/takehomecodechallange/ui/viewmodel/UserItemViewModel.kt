package com.raju.takehomecodechallange.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.takehomecodechallange.data.ItemRepository
import com.raju.takehomecodechallange.data.model.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ItemState {
    data object Loading : ItemState()
    data class Success(val items: Map<Int, List<ItemModel>>) : ItemState()
    data class Error(val message: String) : ItemState()
}

sealed class ItemEvent {
    data object LoadItems : ItemEvent()
    data object RefreshItems : ItemEvent()
}

@HiltViewModel
class UserItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ItemState>(ItemState.Loading)
    val state: StateFlow<ItemState> = _state

    init {
        handleEvent(ItemEvent.LoadItems)
    }

    fun handleEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.LoadItems -> loadItems()
            is ItemEvent.RefreshItems -> loadItems()
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            _state.value = ItemState.Loading
            try {
                val items = repository.getItems()
                _state.value = ItemState.Success(items.groupBy { it.listId })
            } catch (e: Exception) {
                _state.value = ItemState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}