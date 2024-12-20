package com.raju.takehomecodechallange.data

import com.raju.takehomecodechallange.base.MockkUnitTest
import com.raju.takehomecodechallange.data.model.ItemModel
import com.raju.takehomecodechallange.network.api.UserItemApi
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ItemRepositoryTest : MockkUnitTest() {

    @RelaxedMockK
    private lateinit var api: UserItemApi

    private lateinit var repository: ItemRepository

    @Before
    override fun onCreate() {
        super.onCreate()
        repository = ItemRepository(api)
    }

    @Test
    fun `getItems should filter blank names and sort correctly`() = runTest {
        // Given
        val mockItems = listOf(
            ItemModel(1, 2, "Item 1"),
            ItemModel(2, 1, "Item 2"),
            ItemModel(3, 1, ""),
            ItemModel(4, 2, null),
            ItemModel(5, 1, "Item 5")
        )
        coEvery { api.getItems() } returns mockItems

        // When
        val result = repository.getItems()

        // Then
        assertEquals(3, result.size)
        assertTrue(result.none { it.name.isNullOrBlank() })

        // Check sorting
        val expectedOrder = listOf(
            ItemModel(2, 1, "Item 2"),
            ItemModel(5, 1, "Item 5"),
            ItemModel(1, 2, "Item 1")
        )
        assertEquals(expectedOrder, result)
    }
}