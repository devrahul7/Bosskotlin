package com.example.c36a.view

import android.app.Activity
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.c36a.model.UserModel
import com.example.c36a.repository.UserRepositoryImpl
import com.example.c36a.viewmodel.UserViewModel

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                RegBody(innerPadding)
            }
        }
    }
}

@Composable
fun RegBody(innerPaddingValues: PaddingValues) {

    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var selectedOptionText by remember { mutableStateOf("Select Country") }

    val options = listOf("Nepal", "India", "China", "USA", "UK", "Canada", "Australia", "Japan", "South Korea")

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // Gaming color scheme
    val primaryGreen = Color(0xFF00FF88) // Neon green accent
    val secondaryPurple = Color(0xFF9C27B0) // Gaming purple
    val accentBlue = Color(0xFF00E5FF) // Cyan blue
    val darkBackground = Color(0xFF0D1117) // Dark background
    val cardBackground = Color(0xFF161B22) // Slightly lighter dark
    val textPrimary = Color(0xFFFFFFFF) // White text
    val textSecondary = Color(0xFFB0B0B0) // Gray text
    val borderColor = Color(0xFF30363D) // Dark border

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        darkBackground,
                        Color(0xFF1C2128),
                        darkBackground
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(innerPaddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Gaming Brand Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Gaming controller icon with glow effect
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    primaryGreen.copy(alpha = 0.3f),
                                    Color.Transparent
                                ),
                                radius = 100f
                            ),
                            shape = RoundedCornerShape(40.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = primaryGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "GEARNIX",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textPrimary,
                    letterSpacing = 2.sp
                )

                Text(
                    text = "GAMING GEAR STORE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryGreen,
                    letterSpacing = 3.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Level up your gaming experience",
                fontSize = 16.sp,
                color = textSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Registration Card with gaming theme
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = primaryGreen.copy(alpha = 0.1f),
                        spotColor = primaryGreen.copy(alpha = 0.2f)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
            ) {
                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 32.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(primaryGreen, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "CREATE ACCOUNT",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary,
                            letterSpacing = 1.sp
                        )
                    }

                    // Name Fields Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            placeholder = { Text("First Name", color = textSecondary) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = primaryGreen
                                )
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryGreen,
                                unfocusedBorderColor = borderColor,
                                focusedTextColor = textPrimary,
                                unfocusedTextColor = textPrimary,
                                unfocusedLabelColor = textSecondary, // Add this for consistency if you use a label later
                                focusedLabelColor = primaryGreen
                            )
                        )
                        OutlinedTextField(
                            value = lastname,
                            onValueChange = { lastname = it },
                            placeholder = { Text("Last Name", color = textSecondary) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = primaryGreen
                                )
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryGreen,
                                unfocusedBorderColor = borderColor,
                                focusedTextColor = textPrimary,
                                unfocusedTextColor = textPrimary,
                                unfocusedLabelColor = textSecondary,
                                focusedLabelColor = primaryGreen
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email Address", color = textSecondary) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = accentBlue
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentBlue,
                            unfocusedBorderColor = borderColor,
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            unfocusedLabelColor = textSecondary,
                            focusedLabelColor = accentBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Country Dropdown with gaming style
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedOptionText,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                }
                                .clickable { expanded = true },
                            placeholder = { Text("Select Country", color = textSecondary) },
                            enabled = false,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors( // Use OutlinedTextFieldDefaults.colors
                                disabledBorderColor = borderColor, // This replaces disabledIndicatorColor
                                disabledTextColor = textPrimary,
                                disabledLabelColor = textSecondary,
                                focusedBorderColor = secondaryPurple, // Add focused border color
                                unfocusedBorderColor = borderColor, // Add unfocused border color
                                focusedTextColor = textPrimary,
                                unfocusedTextColor = textPrimary
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    tint = secondaryPurple
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = secondaryPurple
                                )
                            }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                                .background(
                                    cardBackground,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            option,
                                            color = textPrimary,
                                            fontSize = 16.sp
                                        )
                                    },
                                    onClick = {
                                        selectedOptionText = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Password", color = textSecondary) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color(0xFFFF6B35) // Orange accent for password
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors( // Use OutlinedTextFieldDefaults.colors
                            focusedBorderColor = Color(0xFFFF6B35), // Corrected parameter name
                            unfocusedBorderColor = borderColor, // Corrected parameter name
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            unfocusedLabelColor = textSecondary,
                            focusedLabelColor = Color(0xFFFF6B35)
                        )
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    // Gaming-style Register Button
                    Button(
                        onClick = {
                            userViewModel.register(email, password) { success, message, userId ->
                                if (success) {
                                    val userModel = UserModel(
                                        userId, email, firstName, lastname, password
                                    )
                                    userViewModel.addUserToDatabase(userId, userModel) { success, msg ->
                                        if (success) {
                                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            primaryGreen,
                                            accentBlue
                                        )
                                    ),
                                    shape = RoundedCornerShape(15.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "JOIN GEARNIX",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Ready to dominate the game?",
                        fontSize = 14.sp,
                        color = textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun RegPreview() {
    RegBody(innerPaddingValues = PaddingValues(0.dp))
}