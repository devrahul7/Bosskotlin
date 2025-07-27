package com.example.c36a.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.c36a.model.ProductModel
import com.example.c36a.repository.ProductRepository
import android.content.Context
import android.net.Uri

class ProductViewModel(val repo: ProductRepository) : ViewModel() {

    fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addProduct(model, callback)
    }

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateProduct(productId, data, callback)
    }

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteProduct(productId, callback)
    }

    private val _products = MutableLiveData<ProductModel?>()
    val products: LiveData<ProductModel?> get() = _products

    private val _allProducts = MutableLiveData<List<ProductModel?>>()
    val allProducts: LiveData<List<ProductModel?>> get() = _allProducts


    fun getProductById(
        productId: String,
    ) {
        repo.getProductById(productId) { success, msg, data ->
            if (success) {
                _products.postValue(data)
            } else {
                _products.postValue(null)

            }
        }
    }

    private var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading


    fun getAllProduct() {
        _loading.postValue(true)
        repo.getAllProduct { success, msg, data ->
            if (success) {
                _loading.postValue(false)
                _allProducts.postValue(data)
            } else {
                _loading.postValue(false)
                _allProducts.postValue(emptyList())

            }
        }
    }
    fun uploadImage(context: Context,imageUri: Uri, callback: (String?) -> Unit){
        repo.uploadImage(context,imageUri,callback)
    }


}