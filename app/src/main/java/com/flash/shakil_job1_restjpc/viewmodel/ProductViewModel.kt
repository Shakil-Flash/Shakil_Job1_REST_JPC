package com.flash.shakil_job1_restjpc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flash.shakil_job1_restjpc.data.model.Product
import com.flash.shakil_job1_restjpc.data.repo.ProductRepository
import com.flash.shakil_job1_restjpc.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(): ViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val products: StateFlow<Resource<List<Product>>> = _products.asStateFlow()

    private var allProducts = listOf<Product>()

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = Resource.Loading()
            try {
                val data = ProductRepository().getProducts()
                Log.d("ProductViewModel", "Loaded ${data.size} products")
                allProducts = data
                _products.value = Resource.Success(data)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error loading products", e)
                _products.value = Resource.Error(e.message ?: "Error")
            }
        }
    }

    fun shuffleProducts() {
        if (allProducts.isNotEmpty()) {
            val shuffledProducts = allProducts.shuffled()
            _products.value = Resource.Success(shuffledProducts)
        }
    }

    fun getProductById(id: Int): Product? {
        return allProducts.find { it.id == id }
    }
}
