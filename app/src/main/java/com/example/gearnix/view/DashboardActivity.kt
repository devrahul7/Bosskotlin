package com.example.gearnix.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.gearnix.R
import com.example.gearnix.model.ProductModel
import com.example.gearnix.repository.ProductRepositoryImpl
import com.example.gearnix.ui.theme.GearnixTheme
import com.example.gearnix.viewmodel.ProductViewModel

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GearnixTheme {
                DashboardBody()
            }
        }
    }
}

@Composable
fun DashboardBody() {
    val context = LocalContext.current
    val activity = context as Activity

    // ViewModel setup
    val repository = remember { ProductRepositoryImpl() }
    val productViewModel = remember { ProductViewModel(repository) }

    // State for profile modal
    var showProfileModal by remember { mutableStateOf(false) }
    var userEmail by remember { mutableStateOf("") }
    var userFullName by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var userAddress by remember { mutableStateOf("") }

    // Observe products with better state management
    val allProducts by productViewModel.allProducts.observeAsState(emptyList())
    val isLoading by productViewModel.loading.observeAsState(false)

    // Load products when activity starts
    LaunchedEffect(Unit) {
        Log.d("DashboardActivity", "Component launched, loading products...")
        productViewModel.getAllProduct()

        // Load user data from SharedPreferences
        val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
        userEmail = sharedPreferences.getString("email", "gamer@gearnix.com") ?: "gamer@gearnix.com"
        userFullName = sharedPreferences.getString("fullName", "Gaming Master") ?: "Gaming Master"
        userPhone = sharedPreferences.getString("phone", "+1234567890") ?: "+1234567890"
        userAddress = sharedPreferences.getString("address", "Gaming HQ") ?: "Gaming HQ"
    }

    // Gaming color scheme
    val backgroundColor = Color(0xFF0D1117)
    val cardColor = Color(0xFF161B22)
    val neonBlue = Color(0xFF00D9FF)
    val neonPurple = Color(0xFF8B5FBF)
    val neonGreen = Color(0xFF39FF14)
    val textColor = Color.White
    val placeholderColor = Color(0xFF8B949E)

    val gradientColors = listOf(
        Color(0xFF0D1117),
        Color(0xFF161B22),
        Color(0xFF21262D)
    )

    // Better product filtering and stats
    val validProducts = allProducts.filterNotNull()
    val totalProducts = validProducts.size
    val totalValue = validProducts.sumOf { it.price }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, AddProductActivity::class.java)
                    context.startActivity(intent)
                },
                containerColor = neonBlue,
                contentColor = Color.Black,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Quick Add Product",
                    modifier = Modifier.size(28.dp)
                )
            }
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
                    .size(300.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                neonBlue.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            radius = 400f
                        ),
                        CircleShape
                    )
                    .align(Alignment.TopEnd)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Gaming Header Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "GEARNIX",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = neonBlue,
                            letterSpacing = 3.sp
                        )
                        Text(
                            text = "GAMING DASHBOARD",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = neonGreen,
                            letterSpacing = 1.5.sp
                        )
                    }

                    Row {
                        IconButton(
                            onClick = {
                                Log.d("DashboardActivity", "Refresh button clicked")
                                productViewModel.refreshProducts()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh Products",
                                tint = neonGreen,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(
                            onClick = { showProfileModal = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = neonPurple,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                                activity.finish()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Logout",
                                tint = neonGreen,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hero Gaming Banner with Image
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = neonBlue.copy(alpha = 0.3f)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Background gradient
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            neonBlue.copy(alpha = 0.2f),
                                            neonPurple.copy(alpha = 0.2f),
                                            neonGreen.copy(alpha = 0.2f)
                                        )
                                    )
                                )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "🎮 WELCOME GAMER!",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = neonBlue,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Manage your epic gaming arsenal",
                                    fontSize = 14.sp,
                                    color = textColor.copy(alpha = 0.8f),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    text = "Level up your inventory game",
                                    fontSize = 12.sp,
                                    color = neonGreen,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            // Gaming controller image
                            Image(
                                painter = painterResource(R.drawable.img),
                                contentDescription = "Gaming Setup",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ FIXED: Gaming Stats Cards with Proper Icons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatsCard(
                        title = "PRODUCTS",
                        value = totalProducts.toString(),
                        icon = Icons.Default.Inventory, // ✅ FIXED: Changed from Warning
                        color = neonBlue,
                        modifier = Modifier.weight(1f)
                    )
                    StatsCard(
                        title = "VALUE",
                        value = "$${String.format("%.0f", totalValue)}",
                        icon = Icons.Default.AttachMoney, // ✅ FIXED: Changed from Face
                        color = neonPurple,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Main Action Buttons Section
                Text(
                    text = "PRODUCT MANAGEMENT",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonBlue,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Primary Action Buttons
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionButton(
                        title = "ADD NEW PRODUCT",
                        subtitle = "Add gaming keyboards, mice & accessories",
                        icon = Icons.Default.Add,
                        backgroundColor = neonBlue,
                        onClick = {
                            val intent = Intent(context, AddProductActivity::class.java)
                            context.startActivity(intent)
                        }
                    )

                    ActionButton(
                        title = "VIEW ALL PRODUCTS",
                        subtitle = "Browse and manage your gaming inventory",
                        icon = Icons.Default.Favorite,
                        backgroundColor = neonGreen,
                        onClick = {
                            val intent = Intent(context, ViewProductActivity::class.java)
                            context.startActivity(intent)
                        }
                    )

                    ActionButton(
                        title = "EDIT PRODUCTS",
                        subtitle = "Update product details and specifications",
                        icon = Icons.Default.Edit,
                        backgroundColor = neonPurple,
                        onClick = {
                            val intent = Intent(context, EditProductActivity::class.java)
                            context.startActivity(intent)
                        }
                    )

                    ActionButton(
                        title = "DELETE PRODUCTS",
                        subtitle = "Remove products from your inventory",
                        icon = Icons.Default.Delete,
                        backgroundColor = Color(0xFFFF6B6B),
                        onClick = {
                            val intent = Intent(context, DeleteProductActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Recent Products Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "YOUR GAMING ARSENAL",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = neonBlue,
                        letterSpacing = 1.sp
                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = neonBlue,
                            strokeWidth = 2.dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Better state management for products display
                when {
                    isLoading -> {
                        // Loading State
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = neonBlue,
                                    strokeWidth = 3.dp,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "LOADING GAMING ARSENAL...",
                                    color = neonBlue,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Preparing your epic gaming gear",
                                    color = placeholderColor,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    validProducts.isEmpty() -> {
                        // Enhanced Empty State with Image
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = cardColor.copy(alpha = 0.6f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Add gaming controller image for empty state
                                Image(
                                    painter = painterResource(R.drawable.img),
                                    contentDescription = "No products",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "NO PRODUCTS ADDED YET",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonPurple,
                                    textAlign = TextAlign.Center,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Start building your gaming arsenal by adding your first epic product",
                                    fontSize = 14.sp,
                                    color = textColor.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        val intent = Intent(context, AddProductActivity::class.java)
                                        context.startActivity(intent)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = neonBlue),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "ADD FIRST PRODUCT",
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }
                        }
                    }

                    else -> {
                        // Products List
                        LazyColumn(
                            modifier = Modifier.height(400.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(validProducts) { product ->
                                ProductCard(
                                    product = product,
                                    cardColor = cardColor,
                                    neonBlue = neonBlue,
                                    neonPurple = neonPurple,
                                    neonGreen = neonGreen,
                                    textColor = textColor,
                                    placeholderColor = placeholderColor,
                                    onEditClick = {
                                        val intent = Intent(context, EditProductActivity::class.java)
                                        intent.putExtra("productId", product.productID)
                                        context.startActivity(intent)
                                    },
                                    onDeleteClick = {
                                        val intent = Intent(context, DeleteProductActivity::class.java)
                                        intent.putExtra("productId", product.productID)
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Enhanced Gaming Footer with Images
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor.copy(alpha = 0.6f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Small gaming icon
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            neonBlue.copy(alpha = 0.3f),
                                            Color.Transparent
                                        )
                                    ),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🎮",
                                fontSize = 24.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "GEARNIX GAMING HUB",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonBlue
                            )
                            Text(
                                text = "Level up your gaming experience with premium accessories",
                                fontSize = 12.sp,
                                color = textColor.copy(alpha = 0.7f),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp)) // Space for FAB
            }
        }

        // Profile Modal
        if (showProfileModal) {
            ProfileModal(
                userFullName = userFullName,
                userEmail = userEmail,
                userPhone = userPhone,
                userAddress = userAddress,
                onDismiss = { showProfileModal = false },
                neonBlue = neonBlue,
                neonPurple = neonPurple,
                neonGreen = neonGreen,
                cardColor = cardColor,
                textColor = textColor,
                placeholderColor = placeholderColor
            )
        }
    }
}

// ✅ FIXED: ActionButton with proper arrow icon
@Composable
fun ActionButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = backgroundColor.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF161B22)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                backgroundColor.copy(alpha = 0.3f),
                                backgroundColor.copy(alpha = 0.1f)
                            )
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = backgroundColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF8B949E),
                    lineHeight = 16.sp
                )
            }

            // ✅ FIXED: Changed from Warning to ArrowForward
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = backgroundColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ProductCard(
    product: ProductModel,
    cardColor: Color,
    neonBlue: Color,
    neonPurple: Color,
    neonGreen: Color,
    textColor: Color,
    placeholderColor: Color,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
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
                .padding(16.dp),
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
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.description,
                    fontSize = 12.sp,
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

            // Action Buttons
            Column {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = neonBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF6B6B),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileModal(
    userFullName: String,
    userEmail: String,
    userPhone: String,
    userAddress: String,
    onDismiss: () -> Unit,
    neonBlue: Color,
    neonPurple: Color,
    neonGreen: Color,
    cardColor: Color,
    textColor: Color,
    placeholderColor: Color
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clickable { }
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = neonBlue.copy(alpha = 0.4f),
                        spotColor = neonBlue.copy(alpha = 0.4f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                )
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PLAYER PROFILE",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = neonBlue,
                            letterSpacing = 2.sp
                        )

                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = neonGreen,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        neonPurple.copy(alpha = 0.3f),
                                        neonBlue.copy(alpha = 0.1f)
                                    )
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Avatar",
                            tint = neonPurple,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = userFullName.ifEmpty { "Gaming Master" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "ELITE GAMER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = neonGreen,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ProfileInfoRow(
                            icon = Icons.Default.Email,
                            label = "GAMING EMAIL",
                            value = userEmail,
                            iconColor = neonBlue,
                            textColor = textColor,
                            placeholderColor = placeholderColor
                        )

                        ProfileInfoRow(
                            icon = Icons.Default.Phone,
                            label = "CONTACT",
                            value = userPhone,
                            iconColor = neonGreen,
                            textColor = textColor,
                            placeholderColor = placeholderColor
                        )

                        ProfileInfoRow(
                            icon = Icons.Default.LocationOn,
                            label = "GAMING HQ",
                            value = userAddress,
                            iconColor = neonPurple,
                            textColor = textColor,
                            placeholderColor = placeholderColor
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonBlue
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = "BACK TO GAME",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    iconColor: Color,
    textColor: Color,
    placeholderColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            iconColor.copy(alpha = 0.2f),
                            iconColor.copy(alpha = 0.05f)
                        )
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = placeholderColor,
                letterSpacing = 0.5.sp
            )

            Text(
                text = value.ifEmpty { "Not provided" },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

@Composable
fun StatsCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = color.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF161B22)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8B949E),
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    GearnixTheme {
        DashboardBody()
    }
}
