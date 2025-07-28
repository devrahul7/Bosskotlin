package com.example.gearnix.view

import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.gearnix.repository.UserRepositoryImpl
import com.example.gearnix.viewmodel.UserViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                LoginBody(innerPadding)
            }
        }
    }
}

@Composable
fun LoginBody(innerPaddingValues: PaddingValues) {

    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    val context = LocalContext.current
    val activity = context as Activity

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // SharedPreferences for Remember Me functionality
    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Load saved credentials if available
    LaunchedEffect(Unit) {
        val localEmail: String = sharedPreferences.getString("email", "") ?: ""
        val localPassword: String = sharedPreferences.getString("password", "") ?: ""

        if (localEmail.isNotEmpty()) {
            username = localEmail
            password = localPassword
            rememberMe = true
        }
    }

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
                    .padding(innerPaddingValues)
                    .padding(24.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Gaming Header with Glow Effect
                Text(
                    text = "GEARNIX",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = neonBlue,
                    textAlign = TextAlign.Center,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "LEVEL UP YOUR GAME",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonGreen,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.5.sp
                )

                Text(
                    text = "Premium Gaming Accessories",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Gaming Controller or Logo Image
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .width(180.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Gaming Login Card with Glow Effect
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
                            .padding(28.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "PLAYER LOGIN",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = neonBlue,
                            modifier = Modifier.padding(bottom = 24.dp),
                            letterSpacing = 1.sp
                        )

                        // Email Field with Gaming Style
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            placeholder = {
                                Text(
                                    "player@gearnix.com",
                                    color = placeholderColor,
                                    fontSize = 14.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
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
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password Field with Gaming Style
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = {
                                Text(
                                    "Enter your password",
                                    color = placeholderColor,
                                    fontSize = 14.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = neonPurple
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisibility = !passwordVisibility }
                                ) {
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
                            visualTransformation = if (passwordVisibility)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
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
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Remember Me and Forgot Password Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = neonGreen,
                                        checkmarkColor = Color.Black,
                                        uncheckedColor = fieldBorderColor
                                    )
                                )
                                Text(
                                    "Keep me logged in",
                                    color = textColor,
                                    fontSize = 14.sp
                                )
                            }

                            Text(
                                "Reset Password",
                                color = neonPurple,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, ResetPasswordActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // ✅ UPDATED: Gaming Login Button with User Data Saving
                        Button(
                            onClick = {
                                if (username.isEmpty() || password.isEmpty()) {
                                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                // Save remember me credentials first
                                if (rememberMe) {
                                    editor.putString("email", username)
                                    editor.putString("password", password)
                                    editor.apply()
                                }

                                userViewModel.login(username, password) { success, message ->
                                    if (success) {
                                        // ✅ NEW: Load and save complete user data after successful login
                                        userViewModel.getUserData(username) { userData ->
                                            val sharedPrefs = context.getSharedPreferences("User", Context.MODE_PRIVATE)
                                            val editor = sharedPrefs.edit()

                                            if (userData != null) {
                                                // Save complete user data to SharedPreferences
                                                editor.putString("email", userData.email)
                                                editor.putString("fullName", userData.fullName)
                                                editor.putString("phone", userData.phoneNumber)
                                                editor.putString("address", userData.address)
                                            } else {
                                                // Fallback: save at least email
                                                editor.putString("email", username)
                                            }

                                            if (rememberMe) {
                                                editor.putString("password", password)
                                            }
                                            editor.apply()
                                        }

                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        val intent = Intent(context, DashboardActivity::class.java)
                                        context.startActivity(intent)
                                        activity.finish()
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
                                "ENTER GAME",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Sign Up Link with Gaming Style
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "New to Gearnix? ",
                                color = placeholderColor,
                                fontSize = 14.sp
                            )
                            Text(
                                "CREATE ACCOUNT",
                                color = neonGreen,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, RegistrationActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
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
                        text = "  OR CONNECT WITH  ",
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
            }
        }
    }
}

@Preview
@Composable
fun LoginPreviewBody() {
    LoginBody(innerPaddingValues = PaddingValues(0.dp))
}
