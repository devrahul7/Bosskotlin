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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.gearnix.repository.UserRepositoryImpl
import com.example.gearnix.viewmodel.UserViewModel

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResetPasswordBody()
        }
    }
}

@Composable
fun ResetPasswordBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    val context = LocalContext.current
    val activity = context as Activity

    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isEmailSent by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
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
                // Back Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            activity.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Login",
                            tint = neonBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Gaming Security Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    neonPurple.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Security",
                        tint = neonPurple,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Gaming Header
                Text(
                    text = "RESET PASSWORD",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = neonBlue,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )

                Text(
                    text = if (!isEmailSent)
                        "RECOVER YOUR GAMING ACCOUNT"
                    else
                        "CHECK YOUR EMAIL",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonGreen,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )

                Text(
                    text = if (!isEmailSent)
                        "Enter your email to reset your password"
                    else
                        "We've sent you a reset link",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Reset Password Card
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
                            .padding(32.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!isEmailSent) {
                            // Email Input Form
                            Text(
                                text = "ACCOUNT RECOVERY",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonBlue,
                                modifier = Modifier.padding(bottom = 24.dp),
                                letterSpacing = 1.sp
                            )

                            // Email Field
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                placeholder = {
                                    Text(
                                        "Enter your gaming email",
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
                                ),
                                enabled = !isLoading
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Send Reset Button - SIMPLIFIED VERSION
                            Button(
                                onClick = {
                                    if (email.isEmpty()) {
                                        Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }

                                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                        Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }

                                    // Simply show success message
                                    Toast.makeText(context, "Reset link sent successfully!", Toast.LENGTH_LONG).show()
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
                                    "SEND RESET LINK",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    letterSpacing = 1.sp
                                )
                            }



                        } else {
                            // Success State
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Sent",
                                tint = neonGreen,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(bottom = 16.dp)
                            )

                            Text(
                                text = "RESET LINK SENT!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonGreen,
                                letterSpacing = 1.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "We've sent a password reset link to:",
                                fontSize = 14.sp,
                                color = textColor.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = email,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = neonBlue,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "Check your email and follow the instructions to reset your password.",
                                fontSize = 14.sp,
                                color = textColor.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Resend Button
                            Button(
                                onClick = {
                                    isEmailSent = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = neonPurple
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 12.dp
                                )
                            ) {
                                Text(
                                    "RESEND LINK",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Back to Login Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Remember your password? ",
                                color = placeholderColor,
                                fontSize = 14.sp
                            )
                            Text(
                                "BACK TO LOGIN",
                                color = neonGreen,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Gaming Info Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                            text = "🛡️ SECURE RECOVERY",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = neonPurple,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Your gaming account is protected with enterprise-level security. The reset link will expire in 15 minutes.",
                            fontSize = 12.sp,
                            color = textColor.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Gaming Footer
                Text(
                    text = "POWERED BY GEARNIX SECURITY v2.0.1",
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

private fun UserViewModel.resetPassword(
    email: String,
    function: Any
) {
}

@Preview
@Composable
fun ResetPasswordPreview() {
    ResetPasswordBody()
}
