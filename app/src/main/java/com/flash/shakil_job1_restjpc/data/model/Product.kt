package com.flash.shakil_job1_restjpc.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    @SerialName("price")
    val price: Double,
    val description: String,
    @SerialName("category")
    val category: CategoryObject?,
    @SerialName("images")
    val images: List<String>?
) {
    // Helper to get first image or placeholder
    val image: String
        get() = images?.firstOrNull() ?: ""
}

@Serializable
data class CategoryObject(
    val id: Int,
    val name: String,
    val slug: String
)
