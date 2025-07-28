package com.example.gearnix.model

data class GamingProduct(
    var id: String = "",
    var name: String = "",
    var category: String = "",
    var price: String = "",
    var imageUrl: String = "",
    var description: String = "",
    var timestamp: Long = System.currentTimeMillis()
) {
    // Empty constructor required for Firestore
    constructor() : this("", "", "", "", "", "", 0L)
}
