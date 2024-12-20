package com.raju.takehomecodechallange.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ItemModel(
    @Json(name = "id") val id: Int,
    @Json(name = "listId") val listId: Int,
    @Json(name = "name") val name: String?
) : Parcelable