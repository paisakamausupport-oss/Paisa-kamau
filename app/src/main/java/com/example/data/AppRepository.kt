package com.example.data

import android.content.Context
import com.example.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

data class TaskItem(
    val taskId: String = "",
    val title: String = "",
    val rewardCoins: Long = 0,
    val videoUrl: String = "",
    val durationSeconds: Long = 0
)

class AppRepository(private val db: FirebaseFirestore) {
    private val auth = Firebase.auth

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in.")
    }

    fun observeTasks(): Flow<List<TaskItem>> {
        return db.collection("tasks")
            .whereEqualTo("active", true)
            .addSnapshotListener { snapshot, _ -> } // Placeholder for actual Flow conversion
            .let { /* Need proper conversion */ emptyFlow() }
    }

    suspend fun markTaskComplete(taskId: String, coins: Long) {
        val uid = requireUserId()
        // Server-side verification placeholder (Cloud Function call would go here)
        val transactionData = mapOf(
            "uid" to uid,
            "type" to "EARN",
            "amount" to coins,
            "status" to "COMPLETED",
            "createdAt" to com.google.firebase.Timestamp.now(),
            "referenceId" to taskId
        )
        db.collection("transactions").add(transactionData).await()
        db.collection("users").document(uid)
            .update("totalCoins", com.google.firebase.firestore.FieldValue.increment(coins))
            .await()
    }
}
