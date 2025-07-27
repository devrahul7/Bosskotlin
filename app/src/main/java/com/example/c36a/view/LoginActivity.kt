package com.example.c36a.view

import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.c36a.repository.UserRepositoryImpl
import com.example.c36a.viewmodel.UserViewModel
import com.example.c36a.R

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

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val localEmail: String = sharedPreferences.getString("email", "").toString()
    val localPassword: String = sharedPreferences.getString("password", "").toString()

    // Load saved credentials
    if (email.isEmpty()) email = localEmail
    if (password.isEmpty()) password = localPassword

    // Gaming color scheme - Dark theme with neon accents
    val backgroundColor = Color(0xFF0D1117)
    val surfaceColor = Color(0xFF161B22)
    val cardColor = Color(0xFF21262D)
    val neonBlue = Color(0xFF00D9FF)
    val neonPurple = Color(0xFF8B5FBF)
    val neonGreen = Color(0xFF39FF14)
    val textPrimary = Color(0xFFFFFFFF)
    val textSecondary = Color(0xFFB1BAC4)
    val borderColor = Color(0xFF30363D)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        backgroundColor,
                        surfaceColor,
                        cardColor
                    ),
                    radius = 1000f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(innerPaddingValues)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Gaming App Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Gamepad,
                    contentDescription = null,
                    tint = neonBlue,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "GEAR",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = neonBlue,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "NIX",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = neonPurple,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "LEVEL UP YOUR SETUP",
                fontSize = 14.sp,
                color = neonGreen,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Text(
                text = "Sign in to access premium gaming gear",
                fontSize = 14.sp,
                color = textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Gaming Login Card with Glow Effect
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = neonBlue.copy(alpha = 0.3f),
                        spotColor = neonBlue.copy(alpha = 0.3f)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                ) {
                    // Gaming-style header
                    Text(
                        text = "ACCESS GRANTED",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = neonGreen,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Enter your credentials",
                        fontSize = 14.sp,
                        color = textSecondary,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    // Gaming Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                "EMAIL ADDRESS",
                                color = textSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = neonBlue
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = neonBlue,
                            unfocusedBorderColor = borderColor,
                            focusedLabelColor = neonBlue,
                            unfocusedLabelColor = textSecondary,
                            unfocusedTextColor = textPrimary,
                            focusedTextColor = textPrimary,
                            cursorColor = neonBlue,
                            focusedContainerColor = backgroundColor.copy(alpha = 0.5f),
                            unfocusedContainerColor = backgroundColor.copy(alpha = 0.3f),
                            focusedPlaceholderColor = textSecondary.copy(alpha = 0.7f),
                            unfocusedPlaceholderColor = textSecondary.copy(alpha = 0.5f)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Gaming Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(
                                "PASSWORD",
                                color = textSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
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
                                    painter = painterResource(
                                        if (passwordVisibility)
                                            R.drawable.baseline_visibility_24
                                        else
                                            R.drawable.baseline_visibility_off_24
                                    ),
                                    contentDescription = null,
                                    tint = neonPurple
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
                            focusedBorderColor = neonPurple,
                            unfocusedBorderColor = borderColor,
                            focusedLabelColor = neonPurple,
                            unfocusedLabelColor = textSecondary,
                            unfocusedTextColor = textPrimary,
                            focusedTextColor = textPrimary,
                            cursorColor = neonPurple,
                            focusedContainerColor = backgroundColor.copy(alpha = 0.5f),
                            unfocusedContainerColor = backgroundColor.copy(alpha = 0.3f),
                            focusedPlaceholderColor = textSecondary.copy(alpha = 0.7f),
                            unfocusedPlaceholderColor = textSecondary.copy(alpha = 0.5f)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Gaming-style options row
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
                                    checkmarkColor = backgroundColor,
                                    uncheckedColor = borderColor
                                )
                            )
                            Text(
                                "REMEMBER",
                                color = textSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            )
                        }

                        TextButton(
                            onClick = { /* Handle forgot password */ }
                        ) {
                            Text(
                                "FORGOT?",
                                color = neonBlue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Gaming Login Button with Gradient
                    Button(
                        onClick = {
                            if (rememberMe) {
                                editor.putString("email", email)
                                editor.putString("password", password)
                                editor.apply()
                            }

                            userViewModel.login(email, password) { success, message ->
                                if (success) {
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
                                        colors = listOf(neonBlue, neonPurple)
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Sign In",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Gaming Sign Up Link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "NEW PLAYER? ",
                            color = textSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            "CREATE ACCOUNT",
                            color = neonGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                val intent = Intent(context, RegistrationActivity::class.java)
                                context.startActivity(intent)
                                activity.finish()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom gaming branding
            Text(
                text = "SECURE GAMING PORTAL",
                fontSize = 10.sp,
                color = textSecondary.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    LoginBody(innerPaddingValues = PaddingValues(0.dp))
}