package com.example.c36a.repository

import com.example.c36a.model.ProductModel
import android.content.Context
import android.net.Uri
interface ProductRepository {

    /*
    success : true,
    message : "product deleted succesfully"
     */
    fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateProduct(
        houseId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteProduct(
        houseId: String,
        callback: (Boolean, String) -> Unit
    )

    /*
  success : true,
  message : "house fetched succesfully"
   */
    fun getProductById(
        houseId: String,
        callback: (Boolean, String, ProductModel?) -> Unit
    )

    fun getAllProduct(callback: (Boolean, String,
                                 List<ProductModel?>) -> Unit)

    fun uploadImage(
        context: Context, imageUri: Uri,
        callback: (String?) -> Unit
    )

    fun getFileNameFromUri(context: Context, uri: Uri): String?
    //present - true
    //absent - false
//    fun attendance(name:String,callback: (Boolean) -> Unit)
}