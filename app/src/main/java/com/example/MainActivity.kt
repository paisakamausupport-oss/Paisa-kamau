package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.ui.auth.AuthScreen
import com.example.ui.components.PaisaKamauCard
import com.example.ui.theme.PaisaKamauTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      PaisaKamauTheme {
        var currentUser by remember { mutableStateOf(Firebase.auth.currentUser) }
        if (currentUser == null) {
          AuthScreen(onAuthSuccess = { currentUser = Firebase.auth.currentUser })
        } else {
          MainScreen()
        }
      }
    }
  }
}

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Ads", "Wallet", "Profile")
    val icons = listOf(Icons.Default.Home, Icons.Default.PlayArrow, Icons.Default.AccountBalanceWallet, Icons.Default.Person)

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (selectedItem) {
                0 -> {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Dashboard", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(16.dp))
                        PaisaKamauCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text("Available Balance", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("रू 1,250.00", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                1 -> Text("Ads Screen")
                2 -> Text("Wallet Screen")
                3 -> Text("Profile Screen")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  PaisaKamauTheme { Greeting("Android") }
}
