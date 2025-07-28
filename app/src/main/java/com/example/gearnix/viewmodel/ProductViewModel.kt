package com.example.gearnix.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gearnix.model.ProductModel
import com.example.gearnix.repository.ProductRepository

class ProductViewModel(val repo: ProductRepository) : ViewModel() {

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        repo.uploadImage(context, imageUri, callback)
    }

    fun addProduct(model: ProductModel, callback: (Boolean, String) -> Unit) {
        repo.addProduct(model, callback)
    }

    fun deleteProduct(productID: String, callback: (Boolean, String) -> Unit) {
        repo.deleteProduct(productID, callback)
    }

    fun updateProduct(productID: String, productData: MutableMap<String, Any?>, callback: (Boolean, String) -> Unit) {
        repo.updateProduct(productID, productData, callback)
    }

    private val _products = MutableLiveData<ProductModel?>()
    val products: LiveData<ProductModel?> get() = _products

    fun getProductByID(productID: String) {
        repo.getProductByID(productID) { data, success, message ->
            if (success) {
                _products.postValue(data)
            } else {
                _products.postValue(null)
            }
        }
    }

    private val _allProducts = MutableLiveData<List<ProductModel?>>()
    val allProducts: LiveData<List<ProductModel?>> get() = _allProducts

    // 🔥 FIXED: Proper loading state management
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // 🔥 FIXED: Improved getAllProduct with better loading state handling
    fun getAllProduct() {
        _loading.postValue(true)
        Log.d("ProductViewModel", "Starting to load products...")

        repo.getAllProduct { data, success, message ->
            Log.d("ProductViewModel", "Received response - Success: $success, Message: $message, Data size: ${data?.size}")

            // 🔥 CRITICAL FIX: Always set loading to false regardless of success/failure
            _loading.postValue(false)

            if (success) {
                _allProducts.postValue(data)
                Log.d("ProductViewModel", "Products updated successfully: ${data.size} items")
            } else {
                _allProducts.postValue(emptyList())
                Log.d("ProductViewModel", "Failed to load products: $message")
            }
        }
    }

    // 🔥 NEW: Force refresh method (useful after CRUD operations)
    fun refreshProducts() {
        Log.d("ProductViewModel", "Refreshing products...")
        getAllProduct()
    }

    // 🔥 NEW: Clear products method (useful for testing/logout)
    fun clearProducts() {
        _allProducts.postValue(emptyList())
        _loading.postValue(false)
        Log.d("ProductViewModel", "Products cleared")
    }

    // 🔥 NEW: Check if products are loading
    fun isProductsLoading(): Boolean {
        return _loading.value ?: false
    }

    // 🔥 NEW: Get current products count
    fun getProductsCount(): Int {
        return _allProducts.value?.filterNotNull()?.size ?: 0
    }
}
