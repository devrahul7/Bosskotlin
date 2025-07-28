package com.example.gearnix.utils

import android.net.Uri
import com.example.gearnix.model.GamingProduct
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

object FirebaseUtils {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun uploadImageAndAddProduct(
        imageUri: Uri,
        product: GamingProduct,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        onProgress: (Int) -> Unit
    ) {
        val imageRef = storage.reference.child("product_images/${UUID.randomUUID()}.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            onProgress(progress)
        }.addOnSuccessListener {
            // Get download URL
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                // Update product with image URL
                val updatedProduct = product.copy(imageUrl = downloadUri.toString())

                // Add product to Firestore
                addProduct(updatedProduct, onSuccess, onFailure)
            }.addOnFailureListener { exception ->
                onFailure("Failed to get image URL: ${exception.message}")
            }
        }.addOnFailureListener { exception ->
            onFailure("Failed to upload image: ${exception.message}")
        }
    }

    fun loadProducts(
        products: MutableList<GamingProduct>,
        onComplete: () -> Unit
    ) {
        db.collection("gaming_products")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                products.clear()
                for (document in documents) {
                    val product = document.toObject(GamingProduct::class.java)
                    if (product.id.isEmpty()) {
                        product.id = document.id
                    }
                    products.add(product)
                }
                onComplete()
            }
            .addOnFailureListener {
                onComplete()
            }
    }

    private fun addProduct(
        product: GamingProduct,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("gaming_products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                db.collection("gaming_products")
                    .document(documentReference.id)
                    .update("id", documentReference.id)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e ->
                        onFailure(e.message ?: "Failed to update product ID")
                    }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to add product")
            }
    }

    fun updateProduct(
        product: GamingProduct,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("gaming_products")
            .document(product.id)
            .set(product)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to update product")
            }
    }

    fun deleteProduct(
        productId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("gaming_products")
            .document(productId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to delete product")
            }
    }
}
