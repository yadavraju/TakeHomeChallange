package com.raju.takehomecodechallange.ui.view

import LoadingScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.raju.takehomecodechallange.ui.viewmodel.ItemEvent
import com.raju.takehomecodechallange.ui.viewmodel.ItemState
import com.raju.takehomecodechallange.ui.viewmodel.UserItemViewModel

@Composable
fun UserItemScreen(
    modifier: Modifier = Modifier,
    viewModel: UserItemViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            is ItemState.Loading -> LoadingScreen()
            is ItemState.Success -> ItemListScreen(
                items = (state as ItemState.Success).items,
                modifier = Modifier.fillMaxSize()
            )

            is ItemState.Error -> ErrorScreen(
                message = (state as ItemState.Error).message,
                onRetry = { viewModel.handleEvent(ItemEvent.RefreshItems) }
            )
        }
    }
}