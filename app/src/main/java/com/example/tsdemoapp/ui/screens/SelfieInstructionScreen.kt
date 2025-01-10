package com.example.tsdemoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tsdemoapp.R
import com.example.tsdemoapp.ui.components.InstructionItem
import com.example.tsdemoapp.ui.components.PrimaryButton
import com.example.tsdemoapp.ui.nav.Routes

@Composable
fun SelfieInstructionScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Selfie of Yourself",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Image
        Box(
            modifier = Modifier
//                .size(150.dp
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_face),
                contentDescription = "Selfie Icon",
                modifier = Modifier.size(200.dp)
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Instructions
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            InstructionItem(text = "Ensure the face is fully visible")
            Spacer(modifier = Modifier.height(8.dp))
            InstructionItem(text = "No face coverings or glasses")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PrimaryButton(text = "I'm Ready") {
                navController.navigate(Routes.TakeSelfie.name)
            }
        }
    }
}

