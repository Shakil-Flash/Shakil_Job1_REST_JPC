package com.flash.shakil_job1_restjpc.data.repo

import com.flash.shakil_job1_restjpc.data.api.ApiClient
import com.flash.shakil_job1_restjpc.data.api.ApiService
import com.flash.shakil_job1_restjpc.data.model.Product

class ProductRepository {

    private val api: ApiService = ApiClient.api

    suspend fun getProducts(): List<Product> {
        return api.getProducts()
    }
}