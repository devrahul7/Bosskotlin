package com.example.gearnix.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.gearnix.R
import com.example.gearnix.model.UserModel
import com.example.gearnix.repository.UserRepositoryImpl
import com.example.gearnix.viewmodel.UserViewModel

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationBody()
        }
    }
}

@Composable
fun RegistrationBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = context as? Activity
    val scrollState = rememberScrollState()

    // Gaming color scheme - Dark with neon accents
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = gradientColors,
                        radius = 1000f
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

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                neonPurple.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            radius = 200f
                        ),
                        CircleShape
                    )
                    .align(Alignment.BottomStart)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Gaming Header
                Text(
                    text = "JOIN GEARNIX",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = neonBlue,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "CREATE YOUR GAMING PROFILE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonGreen,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Level up with premium gaming accessories",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Gaming Image/Icon
                Box(
                    modifier = Modifier
                        .size(160.dp)
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
                        fontSize = 48.sp
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Registration Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = neonBlue.copy(alpha = 0.3f),
                            spotColor = neonBlue.copy(alpha = 0.3f)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "PLAYER REGISTRATION",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = neonBlue,
                            modifier = Modifier.padding(bottom = 20.dp),
                            letterSpacing = 1.sp
                        )

                        // Full Name Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = neonGreen
                                )
                            },
                            placeholder = {
                                Text("Gamer Tag / Full Name", color = placeholderColor)
                            },
                            value = fullName,
                            onValueChange = { fullName = it },
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

                        Spacer(modifier = Modifier.height(15.dp))

                        // Email Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = neonPurple
                                )
                            },
                            placeholder = {
                                Text("gamer@gearnix.com", color = placeholderColor)
                            },
                            value = email,
                            onValueChange = { email = it },
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

                        Spacer(modifier = Modifier.height(15.dp))

                        // Password Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = if (passwordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = neonGreen
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                    Icon(
                                        imageVector = if (passwordVisibility)
                                            Icons.Default.Star
                                        else
                                            Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = neonGreen
                                    )
                                }
                            },
                            placeholder = {
                                Text("Create Password", color = placeholderColor)
                            },
                            value = password,
                            onValueChange = { password = it },
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

                        Spacer(modifier = Modifier.height(15.dp))

                        // Confirm Password Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = neonPurple
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                                    Icon(
                                        imageVector = if (confirmPasswordVisibility)
                                            Icons.Default.Star
                                        else
                                            Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = neonPurple
                                    )
                                }
                            },
                            placeholder = {
                                Text("Confirm Password", color = placeholderColor)
                            },
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
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

                        Spacer(modifier = Modifier.height(15.dp))

                        // Phone Number Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = neonGreen
                                )
                            },
                            placeholder = {
                                Text("Gaming Contact Number", color = placeholderColor)
                            },
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
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

                        Spacer(modifier = Modifier.height(15.dp))

                        // Address Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = neonPurple
                                )
                            },
                            placeholder = {
                                Text("Gaming Setup Address", color = placeholderColor)
                            },
                            value = address,
                            onValueChange = { address = it },
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

                        Spacer(modifier = Modifier.height(20.dp))

                        // Terms and Conditions Checkbox
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = agreeToTerms,
                                onCheckedChange = { agreeToTerms = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = neonGreen,
                                    checkmarkColor = Color.Black,
                                    uncheckedColor = fieldBorderColor
                                )
                            )
                            Text(
                                text = "I agree to Gearnix Terms & Gaming Policy",
                                modifier = Modifier.padding(start = 8.dp),
                                color = textColor,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        // ✅ FIXED: Register Button with SharedPreferences saving
                        Button(
                            onClick = {
                                // Same validation logic
                                if (fullName.isBlank() || email.isBlank() || phoneNumber.isBlank() ||
                                    password.isBlank() || confirmPassword.isBlank() || address.isBlank()) {
                                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (password != confirmPassword) {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (!agreeToTerms) {
                                    Toast.makeText(context, "Please agree to terms and conditions", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                userViewModel.register(email, password) { success, message, userId ->
                                    if (success) {
                                        val userModel = UserModel(
                                            userId, fullName, email, "", phoneNumber, address
                                        )
                                        userViewModel.addUserToDatabase(userId, userModel) { success, message ->
                                            if (success) {
                                                // ✅ NEW: Save user data to SharedPreferences for profile display
                                                val sharedPreferences = context.getSharedPreferences("User", android.content.Context.MODE_PRIVATE)
                                                val editor = sharedPreferences.edit()
                                                editor.putString("email", email)
                                                editor.putString("fullName", fullName)
                                                editor.putString("phone", phoneNumber)
                                                editor.putString("address", address)
                                                editor.apply()

                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                                val intent = Intent(context, LoginActivity::class.java)
                                                context.startActivity(intent)
                                                activity?.finish()
                                            } else {
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = neonBlue
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            Text(
                                "JOIN THE GAME",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Login Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Already a Gearnix player? ",
                                color = placeholderColor,
                                fontSize = 14.sp
                            )
                            Text(
                                "LOGIN NOW",
                                color = neonGreen,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                    activity?.finish()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Gaming-themed Divider
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        neonPurple.copy(alpha = 0.5f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    Text(
                        text = "  OR SIGN UP WITH  ",
                        color = placeholderColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        neonPurple.copy(alpha = 0.5f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Gaming Social Registration Options
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "Google gaming registration coming soon!", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "G",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "Discord gaming registration coming soon!", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🎮",
                                fontSize = 24.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "Steam gaming registration coming soon!", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "S",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonBlue
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Gaming Footer
                Text(
                    text = "POWERED BY GEARNIX ENGINE v2.0.1",
                    fontSize = 10.sp,
                    color = placeholderColor.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun RegistrationPreviewBody() {
    RegistrationBody()
}
