package com.example.gearnix

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.gearnix.view.SplashBody
import com.example.gearnix.view.LoginActivity
import com.example.gearnix.view.DashboardActivity
import com.example.gearnix.ui.theme.GearnixTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GearnixTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val context = LocalContext.current
    val showSplash = remember { mutableStateOf(true) }

    if (showSplash.value) {
        SplashBody()

        LaunchedEffect(Unit) {
            delay(3000) // Show splash for 3 seconds
            showSplash.value = false

            // Navigate based on user login status
            val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            val localEmail = sharedPreferences.getString("email", "")

            if (localEmail.isNullOrEmpty()) {
                // Navigate to Login
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            } else {
                // Navigate to Dashboard
                val intent = Intent(context, DashboardActivity::class.java)
                context.startActivity(intent)
            }

            (context as ComponentActivity).finish()
        }
    }
}
