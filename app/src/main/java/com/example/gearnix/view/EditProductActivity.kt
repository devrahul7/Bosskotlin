package com.example.gearnix.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.gearnix.model.ProductModel
import com.example.gearnix.repository.ProductRepositoryImpl
import com.example.gearnix.ui.theme.GearnixTheme
import com.example.gearnix.viewmodel.ProductViewModel

class EditProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GearnixTheme {
                EditProductBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductBody() {
    val context = LocalContext.current
    val activity = context as Activity

    // ViewModel setup
    val repository = remember { ProductRepositoryImpl() }
    val productViewModel = remember { ProductViewModel(repository) }

    var searchQuery by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<ProductModel?>(null) }

    // Observe products from ViewModel
    val allProducts by productViewModel.allProducts.observeAsState(emptyList())
    val isLoading by productViewModel.loading.observeAsState(false)

    // Load products when activity starts
    LaunchedEffect(Unit) {
        productViewModel.getAllProduct()
    }

    // Filter products based on search
    val validProducts = allProducts.filterNotNull()
    val filteredProducts = if (searchQuery.isEmpty()) {
        validProducts
    } else {
        validProducts.filter {
            it.productName.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true)
        }
    }

    // Gaming color scheme
    val backgroundColor = Color(0xFF0D1117)
    val cardColor = Color(0xFF161B22)
    val neonBlue = Color(0xFF00D9FF)
    val neonPurple = Color(0xFF8B5FBF)
    val neonGreen = Color(0xFF39FF14)
    val textColor = Color.White
    val placeholderColor = Color(0xFF8B949E)
    val fieldBorderColor = Color(0xFF30363D)

    val gradientColors = listOf(
        Color(0xFF0D1117),
        Color(0xFF161B22),
        Color(0xFF21262D)
    )

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "EDIT GAMING GEAR",
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val intent = Intent(context, DashboardActivity::class.java)
                            context.startActivity(intent)
                            activity.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = neonBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = gradientColors,
                        radius = 1200f
                    )
                )
        ) {
            // Background gaming elements
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                neonBlue.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            radius = 300f
                        ),
                        CircleShape
                    )
                    .align(Alignment.TopEnd)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
            ) {
                // Header
                Text(
                    text = "UPDATE YOUR PRODUCTS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonGreen,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Search Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text("Search products to edit...", color = placeholderColor)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = neonGreen
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = neonBlue,
                        unfocusedBorderColor = fieldBorderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = neonBlue,
                        focusedContainerColor = Color(0xFF0D1117),
                        unfocusedContainerColor = Color(0xFF0D1117)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Loading Indicator
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = neonBlue,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading products to edit...",
                                color = neonBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Products List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredProducts) { product ->
                        EditableProductCard(
                            product = product,
                            onEditClick = {
                                selectedProduct = product
                                showEditDialog = true
                            },
                            cardColor = cardColor,
                            neonBlue = neonBlue,
                            neonPurple = neonPurple,
                            neonGreen = neonGreen,
                            textColor = textColor,
                            placeholderColor = placeholderColor
                        )
                    }

                    if (filteredProducts.isEmpty() && !isLoading) {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = cardColor)
                            ) {
                                Column(
                                    modifier = Modifier.padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "🎮",
                                        fontSize = 48.sp
                                    )
                                    Text(
                                        text = "NO PRODUCTS TO EDIT",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = neonPurple,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Try adjusting your search",
                                        fontSize = 14.sp,
                                        color = placeholderColor,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Edit Product Dialog
            if (showEditDialog && selectedProduct != null) {
                EditProductDialog(
                    product = selectedProduct!!,
                    viewModel = productViewModel,
                    onDismiss = { showEditDialog = false },
                    onSave = {
                        showEditDialog = false
                        productViewModel.getAllProduct() // Refresh list
                    },
                    neonBlue = neonBlue,
                    neonPurple = neonPurple,
                    neonGreen = neonGreen,
                    cardColor = cardColor,
                    textColor = textColor,
                    placeholderColor = placeholderColor,
                    fieldBorderColor = fieldBorderColor
                )
            }
        }
    }
}

@Composable
fun EditableProductCard(
    product: ProductModel,
    onEditClick: () -> Unit,
    cardColor: Color,
    neonBlue: Color,
    neonPurple: Color,
    neonGreen: Color,
    textColor: Color,
    placeholderColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = neonBlue.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Card(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (product.image.isNotEmpty()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.productName,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(neonBlue.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🎮",
                            fontSize = 24.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = placeholderColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "$${product.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonGreen,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Edit Button
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Product",
                    tint = neonBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun EditProductDialog(
    product: ProductModel,
    viewModel: ProductViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    neonBlue: Color,
    neonPurple: Color,
    neonGreen: Color,
    cardColor: Color,
    textColor: Color,
    placeholderColor: Color,
    fieldBorderColor: Color
) {
    val context = LocalContext.current

    var editedName by remember { mutableStateOf(product.productName) }
    var editedDescription by remember { mutableStateOf(product.description) }
    var editedPrice by remember { mutableStateOf(product.price.toString()) }
    var isUpdating by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = neonBlue.copy(alpha = 0.4f)
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "EDIT GAMING GEAR",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonBlue,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Product Name Field
                OutlinedTextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    placeholder = { Text("Product Name", color = placeholderColor) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = neonGreen
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = neonBlue,
                        unfocusedBorderColor = fieldBorderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = neonBlue,
                        focusedContainerColor = Color(0xFF0D1117),
                        unfocusedContainerColor = Color(0xFF0D1117)
                    ),
                    enabled = !isUpdating
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Price Field
                OutlinedTextField(
                    value = editedPrice,
                    onValueChange = { editedPrice = it },
                    placeholder = { Text("Price", color = placeholderColor) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = null,
                            tint = neonGreen
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = neonBlue,
                        unfocusedBorderColor = fieldBorderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = neonBlue,
                        focusedContainerColor = Color(0xFF0D1117),
                        unfocusedContainerColor = Color(0xFF0D1117)
                    ),
                    enabled = !isUpdating
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Description Field
                OutlinedTextField(
                    value = editedDescription,
                    onValueChange = { editedDescription = it },
                    placeholder = { Text("Description", color = placeholderColor) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = neonPurple
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = neonBlue,
                        unfocusedBorderColor = fieldBorderColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = neonBlue,
                        focusedContainerColor = Color(0xFF0D1117),
                        unfocusedContainerColor = Color(0xFF0D1117)
                    ),
                    enabled = !isUpdating
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel Button
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF30363D)
                        ),
                        enabled = !isUpdating
                    ) {
                        Text(
                            "CANCEL",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }

                    // Save Button
                    Button(
                        onClick = {
                            val priceValue = editedPrice.toDoubleOrNull()
                            if (priceValue == null || priceValue <= 0) {
                                Toast.makeText(context, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (editedName.isBlank()) {
                                Toast.makeText(context, "Please enter product name", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            isUpdating = true

                            val updateData = mutableMapOf<String, Any?>(
                                "productName" to editedName.trim(),
                                "price" to priceValue,
                                "description" to editedDescription.trim()
                            )

                            viewModel.updateProduct(product.productID, updateData) { success, message ->
                                isUpdating = false
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                if (success) {
                                    onSave()
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = neonBlue),
                        enabled = !isUpdating
                    ) {
                        if (isUpdating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.Black,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "SAVE",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EditProductPreview() {
    GearnixTheme {
        EditProductBody()
    }
}
