package com.raju.takehomecodechallange.network.api

import com.raju.takehomecodechallange.data.model.ItemModel
import retrofit2.http.GET

interface UserItemApi {

    @GET("hiring.json")
    suspend fun getItems(): List<ItemModel>
}