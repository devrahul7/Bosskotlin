package com.example.c36a.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.c36a.R
import com.example.c36a.repository.ProductRepositoryImpl
import com.example.c36a.utils.ImageUtils
import com.example.c36a.viewmodel.ProductViewModel

class UpdateProductActivity : ComponentActivity() {
    lateinit var imageUtils: ImageUtils
    var selectedImageUri by mutableStateOf<Uri?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        imageUtils = ImageUtils(this, this)
        imageUtils.registerLaunchers { uri ->
            selectedImageUri = uri
        }
        setContent {
            MaterialTheme {
                UpdateProductBody(
                    selectedImageUri = selectedImageUri,
                    onPickImage = { imageUtils.launchImagePicker() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDesc by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as? Activity
    val scrollState = rememberScrollState()

    val productId: String? = activity?.intent?.getStringExtra("productId")

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val products = viewModel.products.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.getProductById(productId.toString())
    }

    // Update form fields when product data is loaded
    LaunchedEffect(products.value) {
        products.value?.let { product ->
            productName = product.productName ?: ""
            productDesc = product.productDesc ?: ""
            productPrice = product.productPrice.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D1421),
                        Color(0xFF1A1F2E)
                    )
                )
            )
    ) {
        // Top App Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E2328)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { activity?.finish() },
                    modifier = Modifier
                        .background(
                            Color(0xFF00D4FF).copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF00D4FF)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Update Gaming Gear",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Modify your gaming product",
                        fontSize = 14.sp,
                        color = Color(0xFF8B949E)
                    )
                }
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Image Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E2328)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            tint = Color(0xFF00D4FF),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Product Image",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                2.dp,
                                Color(0xFF00D4FF).copy(alpha = 0.3f),
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { onPickImage() }
                    ) {
                        when {
                            selectedImageUri != null -> {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Selected Gaming Product",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            products.value?.imageURL?.isNotEmpty() == true -> {
                                AsyncImage(
                                    model = products.value?.imageURL,
                                    contentDescription = "Current Gaming Product",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            else -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Image,
                                        contentDescription = null,
                                        tint = Color(0xFF00D4FF),
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Tap to select gaming product image",
                                        color = Color(0xFF8B949E),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        // Edit overlay
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .background(
                                    Color(0xFF00D4FF),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit Image",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Product Details Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E2328)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Product Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Product Name
                    GamingTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = "Product Name",
                        placeholder = "Enter gaming product name"
                    )

                    // Product Description
                    GamingTextField(
                        value = productDesc,
                        onValueChange = { productDesc = it },
                        label = "Description",
                        placeholder = "Describe your gaming gear",
                        singleLine = false,
                        minLines = 3
                    )

                    // Product Price
                    GamingTextField(
                        value = productPrice,
                        onValueChange = { productPrice = it },
                        label = "Price (Rs.)",
                        placeholder = "Enter price"
                    )
                }
            }

            // Update Button
            Button(
                onClick = {
                    if (productName.isBlank() || productPrice.isBlank() || productDesc.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true

                    if (selectedImageUri != null) {
                        viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                            if (imageUrl != null) {
                                updateProduct(viewModel, productId, productName, productDesc, productPrice, imageUrl, context, activity) {
                                    isLoading = false
                                }
                            } else {
                                isLoading = false
                                Toast.makeText(context, "Failed to upload image", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        // Use existing image URL
                        val existingImageUrl = products.value?.imageURL ?: ""
                        updateProduct(viewModel, productId, productName, productDesc, productPrice, existingImageUrl, context, activity) {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00D4FF),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xFF00D4FF).copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Updating...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Icon(
                        Icons.Default.Save,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Update Gaming Gear",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Add some bottom padding
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun GamingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00D4FF),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF8B949E)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            minLines = minLines,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00D4FF),
                unfocusedBorderColor = Color(0xFF30363D),
                focusedContainerColor = Color(0xFF21262D),
                unfocusedContainerColor = Color(0xFF161B22),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFF00D4FF)
            )
        )
    }
}

private fun updateProduct(
    viewModel: ProductViewModel,
    productId: String?,
    productName: String,
    productDesc: String,
    productPrice: String,
    imageUrl: String,
    context: android.content.Context,
    activity: Activity?,
    onComplete: () -> Unit
) {
    val maps = mutableMapOf<String, Any?>()
    maps["productId"] = productId
    maps["productName"] = productName
    maps["productDesc"] = productDesc
    maps["productPrice"] = productPrice.toFloatOrNull() ?: 0
    maps["imageUrl"] = imageUrl

    viewModel.updateProduct(productId.toString(), maps) { success, message ->
        onComplete()
        if (success) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            activity?.finish()
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}

@Preview
@Composable
fun PreviewUpdateProduct() {
    MaterialTheme {
        UpdateProductBody(
            selectedImageUri = null,
            onPickImage = {}
        )
    }
}