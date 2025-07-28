package com.example.gearnix.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import coil.compose.AsyncImage
import com.example.gearnix.R
import com.example.gearnix.model.ProductModel
import com.example.gearnix.repository.ProductRepositoryImpl
import com.example.gearnix.viewmodel.ProductViewModel
import com.example.gearnix.ui.theme.GearnixTheme

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GearnixTheme {
                AddProductScreen()
            }
        }
    }
}

@Composable
fun AddProductScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    AddProductBody(
        selectedImageUri = selectedImageUri,
        onPickImage = {
            imagePickerLauncher.launch("image/*")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var gamingProductName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    // 🎮 Gaming color scheme
    val backgroundColor = Color(0xFF0D1117)
    val cardColor = Color(0xFF161B22)
    val neonBlue = Color(0xFF00D9FF)
    val neonPurple = Color(0xFF8B5FBF)
    val neonGreen = Color(0xFF39FF14)
    val neonOrange = Color(0xFFFF6B35)
    val textColor = Color.White
    val placeholderColor = Color(0xFF8B949E)
    val fieldBorderColor = Color(0xFF30363D)

    val gradientColors = listOf(
        Color(0xFF0D1117),
        Color(0xFF161B22),
        Color(0xFF21262D)
    )

    // 🎮 Animations
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ADD EPIC GAMING GEAR",
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { activity?.finish() }
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
            // 🎮 Gaming Background Elements
            GamingBackgroundParticles(
                neonBlue = neonBlue,
                neonPurple = neonPurple,
                neonGreen = neonGreen
            )

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    // 🎮 Epic Gaming Header Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 16.dp,
                                shape = RoundedCornerShape(24.dp),
                                ambientColor = neonBlue.copy(alpha = 0.4f)
                            )
                            .border(
                                2.dp,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        neonBlue.copy(alpha = glowAlpha),
                                        neonGreen.copy(alpha = glowAlpha),
                                        neonOrange.copy(alpha = glowAlpha)
                                    )
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Rotating Gaming Icon
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
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
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .rotate(rotation)
                                        .border(
                                            3.dp,
                                            brush = Brush.sweepGradient(
                                                colors = listOf(
                                                    neonBlue,
                                                    neonGreen,
                                                    neonOrange,
                                                    neonBlue
                                                )
                                            ),
                                            CircleShape
                                        )
                                )

                                Text(
                                    text = "🎮",
                                    fontSize = 40.sp,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "⚡ LEVEL UP YOUR ARSENAL ⚡",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = neonBlue,
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.5.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Add premium gaming gear to dominate the battlefield",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = neonGreen,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                item {
                    // 🎮 Epic Image Selection Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = neonPurple.copy(alpha = 0.3f)
                            ),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = neonPurple,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "GAMING GEAR SHOWCASE",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonPurple,
                                    letterSpacing = 1.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        3.dp,
                                        if (selectedImageUri != null) {
                                            Brush.linearGradient(
                                                colors = listOf(neonBlue, neonGreen)
                                            )
                                        } else {
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    fieldBorderColor,
                                                    fieldBorderColor.copy(alpha = 0.5f)
                                                )
                                            )
                                        },
                                        RoundedCornerShape(16.dp)
                                    )
                                    .background(Color(0xFF0D1117))
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { onPickImage() },
                                contentAlignment = Alignment.Center
                            ) {
                                if (selectedImageUri != null) {
                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = "Epic Gaming Gear",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(16.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .background(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            neonBlue.copy(alpha = 0.2f),
                                                            Color.Transparent
                                                        )
                                                    ),
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Add Epic Photo",
                                                tint = neonBlue,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            text = "TAP TO ADD GAMING PHOTO",
                                            color = neonBlue,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )

                                        Text(
                                            text = "Showcase your legendary gaming gear",
                                            color = placeholderColor,
                                            fontSize = 12.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    // 🎮 Epic Gaming Form Fields Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = neonGreen.copy(alpha = 0.3f)
                            ),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    tint = neonGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "GEAR SPECIFICATIONS",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonGreen,
                                    letterSpacing = 1.sp
                                )
                            }

                            // Gaming Product Name Field
                            OutlinedTextField(
                                value = gamingProductName,
                                onValueChange = { gamingProductName = it },
                                label = {
                                    Text(
                                        "Gaming Gear Name",
                                        color = placeholderColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "e.g., RGB Mechanical Keyboard, Wireless Gaming Mouse",
                                        color = placeholderColor
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Gaming Gear",
                                        tint = neonGreen
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonBlue,
                                    unfocusedBorderColor = fieldBorderColor,
                                    focusedLabelColor = neonBlue,
                                    focusedTextColor = textColor,
                                    unfocusedTextColor = textColor,
                                    cursorColor = neonBlue,
                                    focusedContainerColor = Color(0xFF0D1117),
                                    unfocusedContainerColor = Color(0xFF0D1117)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )

                            // Price Field
                            OutlinedTextField(
                                value = price,
                                onValueChange = { price = it },
                                label = {
                                    Text(
                                        "Price ($)",
                                        color = placeholderColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "Enter epic gaming gear price",
                                        color = placeholderColor
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Face,
                                        contentDescription = "Price",
                                        tint = neonOrange
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonBlue,
                                    unfocusedBorderColor = fieldBorderColor,
                                    focusedLabelColor = neonBlue,
                                    focusedTextColor = textColor,
                                    unfocusedTextColor = textColor,
                                    cursorColor = neonBlue,
                                    focusedContainerColor = Color(0xFF0D1117),
                                    unfocusedContainerColor = Color(0xFF0D1117)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )

                            // Description Field
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = {
                                    Text(
                                        "Gaming Gear Description",
                                        color = placeholderColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "Describe features, specs, condition, gaming performance...",
                                        color = placeholderColor
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Description",
                                        tint = neonPurple
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 4,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonBlue,
                                    unfocusedBorderColor = fieldBorderColor,
                                    focusedLabelColor = neonBlue,
                                    focusedTextColor = textColor,
                                    unfocusedTextColor = textColor,
                                    cursorColor = neonBlue,
                                    focusedContainerColor = Color(0xFF0D1117),
                                    unfocusedContainerColor = Color(0xFF0D1117)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                item {
                    // 🎮 Epic Add Gaming Gear Button
                    Button(
                        onClick = {
                            when {
                                selectedImageUri == null -> {
                                    Toast.makeText(
                                        context,
                                        "Please select a gaming gear image first",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                gamingProductName.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please enter your epic gaming gear name",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                price.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please enter the price for your gaming gear",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                description.isBlank() -> {
                                    Toast.makeText(
                                        context,
                                        "Please describe your legendary gaming gear",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {
                                    try {
                                        val priceValue = price.toDouble()
                                        viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                                            if (imageUrl != null) {
                                                val model = ProductModel(
                                                    "",
                                                    gamingProductName,
                                                    priceValue,
                                                    description,
                                                    imageUrl
                                                )
                                                viewModel.addProduct(model) { success, message ->
                                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                                    if (success) activity?.finish()
                                                }
                                            } else {
                                                Log.e("Upload Error", "Failed to upload gaming gear image")
                                                Toast.makeText(
                                                    context,
                                                    "Failed to upload gaming image. Please try again.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } catch (e: NumberFormatException) {
                                        Toast.makeText(
                                            context,
                                            "Please enter a valid price for your gaming gear",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .shadow(
                                elevation = 16.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = neonBlue.copy(alpha = 0.5f),
                                spotColor = neonGreen.copy(alpha = 0.5f)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonBlue,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        Color.Black.copy(alpha = 0.2f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "ADD TO GAMING ARSENAL",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.5.sp
                            )
                        }
                    }
                }

                item {
                    // 🎮 Gaming Footer
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardColor.copy(alpha = 0.6f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "⚡ POWERED BY GEARNIX ENGINE ⚡",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonBlue,
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Level up your gaming experience with legendary gear",
                                fontSize = 11.sp,
                                color = textColor.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

// 🎮 Gaming Background Particles
@Composable
fun GamingBackgroundParticles(
    neonBlue: Color,
    neonPurple: Color,
    neonGreen: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetX"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Top right particle
        Box(
            modifier = Modifier
                .size(250.dp)
                .offset(x = offsetX.dp, y = offsetY.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            neonBlue.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        radius = 350f
                    ),
                    CircleShape
                )
                .align(Alignment.TopEnd)
        )

        // Bottom left particle
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = (-offsetX).dp, y = (-offsetY).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.12f),
                            Color.Transparent
                        ),
                        radius = 250f
                    ),
                    CircleShape
                )
                .align(Alignment.BottomStart)
        )

        // Center particle
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (offsetX * 0.6f).dp, y = (offsetY * 0.8f).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            neonGreen.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 150f
                    ),
                    CircleShape
                )
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GearnixAddProductPreview() {
    GearnixTheme {
        AddProductBody(
            selectedImageUri = null,
            onPickImage = {}
        )
    }
}
