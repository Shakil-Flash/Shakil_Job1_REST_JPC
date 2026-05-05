package com.flash.shakil_job1_restjpc.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CartItem(
    @SerialName("product")
    val product: Product,
    @SerialName("quantity")
    val quantity: Int
)
