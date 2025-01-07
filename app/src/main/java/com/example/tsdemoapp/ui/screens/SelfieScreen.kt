package com.example.tsdemoapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tsdemoapp.R
import com.example.tsdemoapp.ui.components.DefaultButton
import com.example.tsdemoapp.ui.components.InstructionItem
import com.example.tsdemoapp.ui.components.PrimaryButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfieScreen(
    navController: NavController
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)) // Light background color
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Spacer to center the content vertically
        Spacer(modifier = Modifier.height(50.dp))

        // Title
        Text(
            text = "Selfie",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Selfie Image
        Image(
            painter = painterResource(id = R.drawable.selfie_photo_sample), // Replace with your image resource
            contentDescription = "Selfie",
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        // Ensure text
        InstructionItem(text = "Ensure your selfie is clear")

        // Spacer for buttons to have some margin
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(text = "Looks good") {
                Toast.makeText(context, "pending", Toast.LENGTH_SHORT).show()
            }
            DefaultButton(text = "Try again") {
                Toast.makeText(context, "pending", Toast.LENGTH_SHORT).show()
            }
        }
    }
}