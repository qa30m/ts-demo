@file:Suppress("DEPRECATION")

package com.example.tsdemoapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun extractNumberAsFloatWithRegex(input: String): Float {
    val regex = Regex("\\d+\\.\\d+|\\d+") // Matches both decimal and whole numbers
    return regex.find(input)?.value?.toFloat()
        ?: 0.0f // Convert to Float or return 0.0 if not found
}

fun formatTimeAgo(timestamp: String): String {
    // Update the format to match your timestamp string
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val lastOnline: Date = sdf.parse(timestamp) ?: return "Just now"
    val now = Date()

    val diffInMillis = now.time - lastOnline.time

    val seconds = (diffInMillis / 1000).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30

    return when {
        months > 0 -> "${months} month${if (months > 1) "s" else ""} ago"
        days > 0 -> "${days} day${if (days > 1) "s" else ""} ago"
        hours > 0 -> "${hours} hour${if (hours > 1) "s" else ""} ago"
        minutes > 0 -> "${minutes} minute${if (minutes > 1) "s" else ""} ago"
        else -> "Just now"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(expiration: String): String {
    return try {
        // Parse the date string
        val dateTime =
            LocalDateTime.parse(expiration, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        // Define the new format
        val formatter = DateTimeFormatter.ofPattern("yyyy MMM dd")

        // Format the date
        dateTime.format(formatter)
    } catch (e: Exception) {
        "N/A" // Return "N/A" if parsing fails
    }
}