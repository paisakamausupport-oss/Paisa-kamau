package com.example.ui.auth

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.R
import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AuthScreen(onAuthSuccess: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Paisa Kamau", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                isLoading = true
                errorMessage = null
                coroutineScope.launch {
                    try {
                        val credentialManager = CredentialManager.create(context)
                        val clientId = context.getString(com.example.R.string.default_web_client_id)
                        
                        val signInOption = GetSignInWithGoogleOption.Builder(serverClientId = clientId).build()
                        val request = GetCredentialRequest.Builder().addCredentialOption(signInOption).build()
                        
                        val result = credentialManager.getCredential(context, request)
                        handleSignIn(result, onAuthSuccess = {
                            isLoading = false
                            onAuthSuccess()
                        }, onError = {
                            isLoading = false
                            errorMessage = it
                        })
                    } catch (e: GetCredentialCancellationException) {
                        isLoading = false
                        Log.w("Auth", "Sign-in cancelled", e)
                    } catch (e: Exception) {
                        isLoading = false
                        errorMessage = e.message
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Signing in..." else "Sign in with Google")
        }
        
        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

private suspend fun handleSignIn(
    result: GetCredentialResponse,
    onAuthSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val credential = result.credential
    if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        try {
            val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
            val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            Firebase.auth.signInWithCredential(authCredential).await()
            onAuthSuccess()
        } catch (e: Exception) {
            onError("Authentication failed: ${e.message}")
        }
    } else {
        onError("Unexpected credential type")
    }
}
