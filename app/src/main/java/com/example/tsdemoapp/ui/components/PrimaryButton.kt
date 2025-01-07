package com.example.tsdemoapp.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp), // Rounded corners
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF428BCA)), // Blue color
        modifier = Modifier
            .padding(16.dp)
            .defaultMinSize(minHeight = 48.dp) // Minimum height
    ) {
        Text(
            text = text,
            color = Color.White, // White text color
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}