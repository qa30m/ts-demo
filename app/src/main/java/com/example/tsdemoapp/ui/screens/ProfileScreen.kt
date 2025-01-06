package com.example.tsdemoapp.ui.screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.material3.*

@Composable
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Divider(color = Color.Gray, thickness = 1.dp)

                ProfileRow(label = "Add Selfie Photo", actionText = "Add Now")
                ProfileRow(label = "Add Identification", actionText = "Add Now")

                ProfileInputField(label = "First Name", placeholder = "enter your firstname")
                ProfileInputField(label = "Last Name", placeholder = "enter your lastname")
                ProfileInputField(label = "Email", placeholder = "enter your email")
                ProfileInputField(label = "Date of Birth", placeholder = "select your birth")
                ProfileDropdownField(
                    label = "Gender",
                    options = listOf("Male", "Female", "Other"),
                    selectedOption = "Male",
                    onOptionSelected = { selectedGender ->
                        // Handle the selected gender here
                        println("Selected Gender: $selectedGender")
                    }
                )
                ProfileInputField(label = "Province", placeholder = "select province")
                ProfileInputField(label = "District", placeholder = "enter your district")
                ProfileInputField(label = "Occupation", placeholder = "enter your occupation")
                ProfileInputField(label = "Nationality", placeholder = "select your nationality")
                ProfileInputField(label = "Monthly Income", placeholder = "Null")
            }

            Button(
                onClick = { /* Handle save action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Save", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ProfileRow(label: String, actionText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(
            text = actionText,
            color = Color.Red,
            modifier = Modifier.clickable { /* Handle click */ }
        )
    }
}
//
//@Composable
//fun ProfileInputField(label: String, placeholder: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = label, modifier = Modifier.weight(1f))
//        TextField(
//            value = "",
//            onValueChange = {},
//            placeholder = { Text(placeholder) },
//            singleLine = true,
//            modifier = Modifier.weight(1f),
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
//        )
//    }
//}

@Composable
fun ProfileDropdownField(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) } // State to manage dropdown visibility
    var selectedText by remember { mutableStateOf(selectedOption) } // State to track selected option

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Box(modifier = Modifier.weight(1f)) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedText,
                    textAlign = TextAlign.End
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown, // Built-in down arrow icon
                    contentDescription = "Dropdown Arrow"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option)
                        },
                        onClick = {
                            selectedText = option
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}