package com.flash.shakil_job1_restjpc.data.model

data class Product(
    val id : Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String?,
    val image: String
)
