package com.example.c36a.repository

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.c36a.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepositoryImpl : ProductRepository {
    val database = FirebaseDatabase.getInstance()
    val ref = database.reference.child("products")

    //add -> setValue()
    //update -> updateChildren()
    //delete -> removeValue()
    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dtsliavfd",
            "api_key" to "825865536694556",
            "api_secret" to "9s8DJxQoG3aVDD3XqbybMY78sXw"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                // ✅ Fix: Remove extensions from file name before upload
                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                //  Run UI updates on the Main Thread
                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    override fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key.toString()
        model.productId = id
        ref.child(id).setValue(model).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product added")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (Boolean, String, ProductModel?) -> Unit
    ) {
        ref.child(productId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var products = snapshot.getValue(ProductModel::class.java)
//                    if(products != null){
//                        callback(true,"Fetched",products)
//                    }

                    products?.let {
                        callback(true,"Fetched",it)
                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,error.message,null)
            }

        })
    }

    override fun getAllProduct(callback: (Boolean, String, List<ProductModel?>) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val allProducts = mutableListOf<ProductModel>()
                if(snapshot.exists()){
                    for(eachData in snapshot.children){
                        var products = eachData.getValue(ProductModel::class.java)
                        if(products != null){
                            allProducts.add(products)
                        }
                    }
                    callback(true,"fetched",allProducts)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,error.message,emptyList())
            }

        })
    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).updateChildren(data).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product updated")
            }else{
                callback(false,"${it.exception?.message}")

            }
        }
    }

    override fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product deleted")
            }else{
                callback(false,"${it.exception?.message}")

            }
        }
    }
}