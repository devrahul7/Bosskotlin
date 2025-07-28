package com.example.gearnix.repository

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.util.Log
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.gearnix.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepositoryImpl : ProductRepository {

    val database = FirebaseDatabase.getInstance()
    val ref = database.reference.child("products")

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dl0ttohkp",
            "api_key" to "163529466517668",
            "api_secret" to "GmgkBQmyK6GTJvCx6CENS8xNGBg"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?
                imageUrl = imageUrl?.replace("http://", "https://")

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
        model.productID = id
        ref.child(model.productID).setValue(model).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Gaming gear added to arsenal successfully!")
                Log.d("ProductRepository", "Product added: ${model.productName}")
            } else {
                callback(false, "Failed to add product: ${it.exception?.message}")
                Log.e("ProductRepository", "Failed to add product: ${it.exception?.message}")
            }
        }
    }

    override fun deleteProduct(
        productID: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productID).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Gaming gear removed from arsenal!")
                Log.d("ProductRepository", "Product deleted: $productID")
            } else {
                callback(false, "Failed to delete product: ${it.exception?.message}")
                Log.e("ProductRepository", "Failed to delete product: ${it.exception?.message}")
            }
        }
    }

    override fun updateProduct(
        productID: String,
        productData: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productID).updateChildren(productData).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Gaming gear updated successfully!")
                Log.d("ProductRepository", "Product updated: $productID")
            } else {
                callback(false, "Failed to update product: ${it.exception?.message}")
                Log.e("ProductRepository", "Failed to update product: ${it.exception?.message}")
            }
        }
    }

    override fun getProductByID(
        productID: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val product = snapshot.getValue(ProductModel::class.java)
                    if (product != null) {
                        callback(product, true, "Product fetched successfully")
                        Log.d("ProductRepository", "Product fetched: ${product.productName}")
                    } else {
                        callback(null, false, "Product data is null")
                        Log.w("ProductRepository", "Product data is null for ID: $productID")
                    }
                } else {
                    callback(null, false, "Product not found")
                    Log.w("ProductRepository", "Product not found for ID: $productID")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, "Database error: ${error.message}")
                Log.e("ProductRepository", "Database error: ${error.message}")
            }
        })
    }

    // 🔥 CRITICAL FIX: Using singleValueEvent instead of continuous listener
    override fun getAllProduct(callback: (List<ProductModel?>, Boolean, String) -> Unit) {
        Log.d("ProductRepository", "Starting to fetch all products...")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("ProductRepository", "Firebase onDataChange called. Snapshot exists: ${snapshot.exists()}")

                try {
                    if (snapshot.exists()) {
                        val allProducts = mutableListOf<ProductModel>()
                        var processedCount = 0
                        val totalChildren = snapshot.childrenCount.toInt()

                        Log.d("ProductRepository", "Found $totalChildren products in Firebase")

                        // 🔥 FIX: Handle empty database case
                        if (totalChildren == 0) {
                            Log.d("ProductRepository", "No products found, returning empty list")
                            callback(emptyList(), true, "No products found")
                            return
                        }

                        // Process each product
                        for (eachProduct in snapshot.children) {
                            try {
                                val product = eachProduct.getValue(ProductModel::class.java)
                                if (product != null) {
                                    allProducts.add(product)
                                    Log.d("ProductRepository", "Added product: ${product.productName}")
                                } else {
                                    Log.w("ProductRepository", "Null product found, skipping")
                                }
                            } catch (e: Exception) {
                                Log.e("ProductRepository", "Error parsing product: ${e.message}")
                            }

                            processedCount++

                            // 🔥 FIX: Call callback after all products are processed
                            if (processedCount == totalChildren) {
                                Log.d("ProductRepository", "Finished processing. Final count: ${allProducts.size}")
                                callback(allProducts, true, "Products fetched successfully")
                            }
                        }
                    } else {
                        Log.d("ProductRepository", "No products exist in database")
                        callback(emptyList(), true, "No products found")
                    }
                } catch (e: Exception) {
                    Log.e("ProductRepository", "Exception in getAllProduct: ${e.message}")
                    callback(emptyList(), false, "Error fetching products: ${e.message}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProductRepository", "Firebase error: ${error.message}")
                callback(emptyList(), false, "Database error: ${error.message}")
            }
        })
    }

    // 🔥 NEW: Method to check if products exist
    fun hasProducts(callback: (Boolean) -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.exists() && snapshot.childrenCount > 0)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    // 🔥 NEW: Get products count
    fun getProductsCount(callback: (Int) -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.childrenCount.toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                callback(0)
            }
        })
    }
}
