package com.raju.takehomecodechallange.data

import com.raju.takehomecodechallange.data.model.ItemModel
import com.raju.takehomecodechallange.network.api.UserItemApi
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val api: UserItemApi
) {
    suspend fun getItems(): List<ItemModel> {
        return api.getItems()
            .filter { !it.name.isNullOrBlank() } // Filter out items with null or blank names
            .sortedWith(
                compareBy<ItemModel> { it.listId }  // First sort by listId
                    .thenBy {
                        // Then sort by name, handling numeric parts properly
                        it.name?.let { name ->
                            // Extract number from "Item X" format
                            name.replace("Item ", "").toIntOrNull() ?: Int.MAX_VALUE
                        } ?: Int.MAX_VALUE
                    }
            )
    }
}