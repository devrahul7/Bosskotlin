package com.example.c36a.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashBody()
        }
    }
}

@Composable
fun SplashBody() {

    val context = LocalContext.current
    val activity = context as Activity

    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val localEmail: String = sharedPreferences.getString("email", "").toString()

    // Animation values
    val scaleAnimation = remember { Animatable(0f) }
    val alphaAnimation = remember { Animatable(0f) }
    val rotationAnimation = remember { Animatable(0f) }
    val pulseAnimation = remember { Animatable(1f) }

    // Gaming color scheme - Dark with neon accents
    val backgroundColor = Color(0xFF0D1117)
    val neonBlue = Color(0xFF00D9FF)
    val neonPurple = Color(0xFF8B5FBF)
    val neonGreen = Color(0xFF39FF14)
    val gradientColors = listOf(
        Color(0xFF0D1117),
        Color(0xFF161B22),
        Color(0xFF21262D)
    )

    LaunchedEffect(Unit) {
        // Start animations
        scaleAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )

        alphaAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            )
        )

        // Continuous rotation for loading indicator
        rotationAnimation.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        // Pulse animation for logo
        pulseAnimation.animateTo(
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    LaunchedEffect(Unit) {
        delay(3000)
        if (localEmail.isEmpty()) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            activity.finish()
        } else {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
            activity.finish()
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = gradientColors,
                        radius = 800f
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Gaming Logo Container with Glow Effect
                Box(
                    modifier = Modifier
                        .scale(scaleAnimation.value * pulseAnimation.value)
                        .alpha(alphaAnimation.value)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    neonBlue.copy(alpha = 0.3f),
                                    Color.Transparent
                                ),
                                radius = 120f
                            ),
                            RoundedCornerShape(24.dp)
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Gaming Controller Icon
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = null,
                            tint = neonBlue,
                            modifier = Modifier.size(52.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        // App Name with Gaming Font Style
                        Text(
                            text = "GAME",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = neonBlue,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "GEAR",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = neonPurple,
                            letterSpacing = 2.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Gaming Tagline
                Text(
                    text = "LEVEL UP YOUR GAME",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonGreen,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.alpha(alphaAnimation.value)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Premium Gaming Accessories",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(alphaAnimation.value)
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Futuristic Loading Indicator
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.alpha(alphaAnimation.value)
                ) {
                    // Outer rotating ring
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .rotate(rotationAnimation.value),
                        color = neonBlue.copy(alpha = 0.6f),
                        strokeWidth = 2.dp
                    )

                    // Middle ring
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .rotate(-rotationAnimation.value * 0.7f),
                        color = neonPurple.copy(alpha = 0.8f),
                        strokeWidth = 3.dp
                    )

                    // Inner core
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = neonGreen,
                        strokeWidth = 2.dp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Loading text with gaming style
                Text(
                    text = "INITIALIZING...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 1.sp,
                    modifier = Modifier.alpha(alphaAnimation.value)
                )
            }

            // Bottom branding with gaming theme
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp)
                    .alpha(alphaAnimation.value),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(neonGreen, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "POWERED BY GAMEGEAR",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(neonBlue, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "v2.0.1 BETA",
                    fontSize = 10.sp,
                    color = neonPurple.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSplash() {
    SplashBody()
}