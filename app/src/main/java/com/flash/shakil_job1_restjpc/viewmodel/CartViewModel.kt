package com.flash.shakil_job1_restjpc.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flash.shakil_job1_restjpc.data.model.CartItem
import com.flash.shakil_job1_restjpc.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

class CartViewModel(private val context: Context) : ViewModel() {

    private val CART_KEY = stringPreferencesKey("cart_items")
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()
    private val gson = Gson()
    private val cartItemType = object : TypeToken<List<CartItem>>() {}.type

    init {
        loadCartFromStorage()
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                val currentCart = _cartItems.value.toMutableList()
                val existingItem = currentCart.find { it.product.id == product.id }

                if (existingItem != null) {
                    val index = currentCart.indexOf(existingItem)
                    currentCart[index] = existingItem.copy(quantity = existingItem.quantity + quantity)
                } else {
                    currentCart.add(CartItem(product, quantity))
                }

                _cartItems.value = currentCart
                saveCartToStorage(currentCart)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedCart = _cartItems.value.filter { it.product.id != productId }
                _cartItems.value = updatedCart
                saveCartToStorage(updatedCart)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                val updatedCart = _cartItems.value.map { item ->
                    if (item.product.id == productId) {
                        item.copy(quantity = quantity)
                    } else {
                        item
                    }
                }
                _cartItems.value = updatedCart
                saveCartToStorage(updatedCart)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                _cartItems.value = emptyList()
                saveCartToStorage(emptyList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCartTotal(): Double {
        return try {
            _cartItems.value.sumOf { it.product.price * it.quantity }
        } catch (e: Exception) {
            0.0
        }
    }

    fun getCartItemCount(): Int {
        return try {
            _cartItems.value.sumOf { it.quantity }
        } catch (e: Exception) {
            0
        }
    }

    private fun saveCartToStorage(cartItems: List<CartItem>) {
        viewModelScope.launch {
            try {
                val json = gson.toJson(cartItems, cartItemType)
                context.dataStore.edit { preferences ->
                    preferences[CART_KEY] = json
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadCartFromStorage() {
        viewModelScope.launch {
            try {
                val preferences = context.dataStore.data.first()
                val json = preferences[CART_KEY] ?: run {
                    _cartItems.value = emptyList()
                    return@launch
                }
                try {
                    val cartItems = gson.fromJson(json, cartItemType) as List<CartItem>
                    _cartItems.value = cartItems
                } catch (e: Exception) {
                    // Clear corrupted data
                    e.printStackTrace()
                    try {
                        context.dataStore.edit { it.remove(CART_KEY) }
                    } catch (e2: Exception) {
                        e2.printStackTrace()
                    }
                    _cartItems.value = emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _cartItems.value = emptyList()
            }
        }
    }
}
