package com.raju.takehomecodechallange.ui.viewmodel

import com.raju.takehomecodechallange.base.MockkUnitTest
import com.raju.takehomecodechallange.data.ItemRepository
import com.raju.takehomecodechallange.data.model.ItemModel
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserItemViewModelTest : MockkUnitTest() {

    @RelaxedMockK
    private lateinit var repository: ItemRepository

    private lateinit var viewModel: UserItemViewModel


    @Before
    override fun onCreate() {
        super.onCreate()
        viewModel = UserItemViewModel(repository)
    }

    @Test
    fun `loadItems success should update state with grouped items`() = runTest {
        // Given
        val testItems = listOf(
            ItemModel(1, 1, "Item 1"),
            ItemModel(2, 1, "Item 2"),
            ItemModel(3, 2, "Item 3")
        )
        coEvery { repository.getItems() } returns testItems

        // When
        viewModel.handleEvent(ItemEvent.LoadItems)

        // Then
        val currentState = viewModel.state.value
        assert(currentState is ItemState.Success)
        val successState = currentState as ItemState.Success
        assertEquals(2, successState.items.size)
        assertEquals(2, successState.items[1]?.size)
        assertEquals(1, successState.items[2]?.size)
    }

    @Test
    fun `loadItems success should update state with grouped items during RefreshItems  `() =
        runTest {
            // Given
            val testItems = listOf(
                ItemModel(1, 1, "Item 1"),
                ItemModel(2, 1, "Item 2"),
                ItemModel(3, 2, "Item 3")
            )
            coEvery { repository.getItems() } returns testItems

            // When
            viewModel.handleEvent(ItemEvent.RefreshItems)

            // Then
            val currentState = viewModel.state.value
            assert(currentState is ItemState.Success)
            val successState = currentState as ItemState.Success
            assertEquals(2, successState.items.size)
            assertEquals(2, successState.items[1]?.size)
            assertEquals(1, successState.items[2]?.size)
        }

    @Test
    fun `loadItems error should update state with error message`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.getItems() } throws Exception(errorMessage)

        // When
        viewModel.handleEvent(ItemEvent.LoadItems)

        // Then
        val currentState = viewModel.state.value
        assert(currentState is ItemState.Error)
        assertEquals(errorMessage, (currentState as ItemState.Error).message)
    }
}