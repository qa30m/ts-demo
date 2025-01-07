package com.example.tsdemoapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tsdemoapp.R
import com.example.tsdemoapp.model.UserProfile
import com.example.tsdemoapp.preferences.UserPreferences
import com.example.tsdemoapp.ui.components.PrimaryButton
import com.example.tsdemoapp.ui.nav.Routes
import com.example.tsdemoapp.ui.theme.GrayBackground
import com.example.tsdemoapp.ui.theme.LatoFontFamily
import com.example.tsdemoapp.ui.theme.MontserratFontFamily
import com.example.tsdemoapp.ui.theme.blackFont
import com.example.tsdemoapp.ui.theme.lightGray
import com.example.tsdemoapp.ui.theme.redFont
import com.example.tsdemoapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.Factory
    ),
) {
    // Get the Context in Composable
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val user = userPreferences.getUser()

    // Observe the user profile and update status
    val userProfile by profileViewModel.userProfile.observeAsState()

    // State for refreshing
    val isRefreshing by profileViewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.updateUserProfile()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 4.dp, top = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "Verify Your Account",
                    color = blackFont,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = MontserratFontFamily,
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = LatoFontFamily,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfo(userProfile, navController)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PrimaryButton(
                        text = "Save Profile",
                    ) {
                        profileViewModel.updateUserProfile()
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfo(
    userProfile: UserProfile?,
    navController: NavController
) {
    val firstName = remember { mutableStateOf(userProfile?.firstName ?: "") }
    val lastName = remember { mutableStateOf(userProfile?.lastName ?: "") }
    val email = remember { mutableStateOf(userProfile?.email ?: "") }
    val dob = remember { mutableStateOf(userProfile?.dob ?: "") }
    val gender = remember { mutableStateOf(userProfile?.gender ?: "") }
    val province = remember { mutableStateOf(userProfile?.province ?: "") }
    val district = remember { mutableStateOf(userProfile?.district ?: "") }
    val occupation = remember { mutableStateOf(userProfile?.occupation ?: "") }
    val nationality = remember { mutableStateOf(userProfile?.nationality ?: "") }
    val monthlyIncome = remember { mutableStateOf(userProfile?.monthlyIncome ?: "") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = lightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            ProfileActionRow(
                "Add Selfie Photo",
                onActionClick = { navController.navigate(Routes.SelfieInstructions.name) }
            )
            BottomBorder()
            ProfileActionRow(
                "Add Identification",
                onActionClick = { navController.navigate(Routes.AddIdentification.name) }
            )
            BottomBorder()
            ProfileInputField(
                label = "First Name",
                text = firstName,
                placeholder = "Enter your first name"
            )

            BottomBorder()
            ProfileInputField(
                label = "Last Name",
                text = lastName,
                placeholder = "Enter your last name"
            )
            BottomBorder()
            ProfileInputField(
                label = "Email",
                text = email,
                placeholder = "Enter your email",
                keyboardType = KeyboardType.Email
            )
            BottomBorder()
            ProfileInputField(
                label = "Date of Birth",
                text = dob,
                placeholder = "Select your birth date"
            )
            BottomBorder()
            ProfileDropdownField(
                label = "Gender",
                options = listOf("Select Gender", "Male", "Female", "Other"),
                selectedOption = "Select Gender",
                onOptionSelected = { selectedOption ->
                    // Handle the selected change here
                }
            )
            BottomBorder()
            ProfileDropdownField(
                label = "Province",
                options = listOf("Select Province", "Kabul", "Balkh", "Herat", "Other"),
                selectedOption = "Select Province",
                onOptionSelected = { selectedOption ->
                    // update districts list
                }
            )
            BottomBorder()
            ProfileDropdownField(
                label = "District",
                options = listOf("Select District",),
                selectedOption = "Select District",
                onOptionSelected = { selectedOption ->
                    // Handle the selected change here
                }
            )
            BottomBorder()
            ProfileDropdownField(
                label = "Occupation",
                options = listOf("Select Occupation", "Engineer", "Doctor", "Teacher", "Artist"),
                selectedOption = "Select Occupation",
                onOptionSelected = { selectedOption ->
                    // Handle the selected change here
                }
            )
            BottomBorder()
            ProfileDropdownField(
                label = "Nationality",
                options = listOf("Select Nationality", "Afghan", "American", "Canadian", "Australian", "Other"),
                selectedOption = "Select Nationality",
                onOptionSelected = { selectedOption ->
                    // Handle the selected change here
                }
            )
            BottomBorder()
            ProfileInputField(
                label = "Monthly Income",
                text = monthlyIncome,
                placeholder = "Enter your monthly income",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        }
    }
}

@Composable
private fun BottomBorder() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .background(blackFont)
            .height(1.dp)
    )
}

@Composable
fun ProfileActionRow(
    title: String,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = blackFont,
            fontWeight = FontWeight.Normal,
        )
        Row(
            modifier = Modifier
                .clickable { onActionClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Add Now",
                fontSize = 16.sp,
                color = redFont,
                fontWeight = FontWeight.Normal,
            )
            Icon(
                painter = painterResource(R.drawable.arrow_1),
                contentDescription = "",
                tint = redFont,
                modifier = Modifier
                    .size(32.dp)
                    .padding(start = 8.dp, end = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInputField(
    label: String,
    text: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
//    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = blackFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        TextField(
            textStyle = TextStyle(color = Black, fontSize = 14.sp, textAlign = TextAlign.End),
            value = text.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            onValueChange = {
                text.value = it
            },
            placeholder = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(placeholder, fontSize = 14.sp)
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GrayBackground,
                unfocusedBorderColor = GrayBackground
            ),
            modifier = Modifier
                .weight(2f)
                .height(52.dp),
        )
    }
}

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
        Box(
            modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
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
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
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
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}