package com.example.c36a.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.c36a.model.ProductModel
import com.example.c36a.repository.ProductRepositoryImpl
import com.example.c36a.utils.ImageUtils
import com.example.c36a.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {
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
            GamingTheme {
                AddProductBody(
                    selectedImageUri = selectedImageUri,
                    onPickImage = { imageUtils.launchImagePicker() }
                )
            }
        }
    }
}

@Composable
fun GamingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF00D4FF),
            secondary = Color(0xFF9C27B0),
            background = Color(0xFF0A0A0A),
            surface = Color(0xFF1A1A1A),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDesc by remember { mutableStateOf("") }

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    // Gaming color scheme
    val primaryColor = Color(0xFF00D4FF)
    val secondaryColor = Color(0xFF9C27B0)
    val backgroundColor = Color(0xFF0A0A0A)
    val surfaceColor = Color(0xFF1A1A1A)
    val accentColor = Color(0xFFFF6B35)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor,
                        Color(0xFF1A1A2E),
                        backgroundColor
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))

                // Gaming Header
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ADD GAMING GEAR",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "Level up your inventory",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Product Name Field
                GamingInputField(
                    label = "Product Name",
                    value = productName,
                    onValueChange = { productName = it },
                    icon = Icons.Default.SportsEsports,
                    placeholder = "e.g., RGB Gaming Keyboard"
                )

                // Price Field
                GamingInputField(
                    label = "Price (₹)",
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    icon = Icons.Default.AttachMoney,
                    placeholder = "e.g., 2999"
                )

                // Description Field
                GamingInputField(
                    label = "Description",
                    value = productDesc,
                    onValueChange = { productDesc = it },
                    icon = Icons.Default.Description,
                    placeholder = "Describe your gaming accessory...",
                    minLines = 3
                )

                // Product Image Section
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Product Image",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onPickImage() },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(primaryColor, secondaryColor)
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            if (selectedImageUri != null) {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Selected Gaming Product",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(14.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null,
                                        tint = primaryColor,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "TAP TO ADD IMAGE",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = "Show off your gaming gear",
                                        fontSize = 12.sp,
                                        color = Color.Gray.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Gaming Submit Button
                Button(
                    onClick = {
                        if (selectedImageUri != null && productName.isNotEmpty() &&
                            productPrice.isNotEmpty() && productDesc.isNotEmpty()) {
                            viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                                if (imageUrl != null) {
                                    val model = ProductModel(
                                        "",
                                        productName,
                                        productPrice.toDoubleOrNull() ?: 0.0,
                                        productDesc,
                                        imageUrl
                                    )
                                    viewModel.addProduct(model) { success, message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        if (success) activity?.finish()
                                    }
                                } else {
                                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please fill all fields and select an image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(primaryColor, secondaryColor, accentColor)
                                ),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "LAUNCH PRODUCT",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamingInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String = "",
    minLines: Int = 1
) {
    val primaryColor = Color(0xFF00D4FF)
    val surfaceColor = Color(0xFF1A1A1A)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    placeholder,
                    color = Color.Gray.copy(alpha = 0.6f)
                )
            },
            minLines = minLines,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors( // Corrected call for Material 3
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                // Specify container colors for both focused and unfocused states
                focusedContainerColor = surfaceColor.copy(alpha = 0.5f),
                unfocusedContainerColor = surfaceColor.copy(alpha = 0.5f),
                cursorColor = primaryColor
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGamingAddProduct() {
    GamingTheme {
        AddProductBody(
            selectedImageUri = null,
            onPickImage = {}
        )
    }
}