package com.example.tsdemoapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.tsdemoapp.ui.theme.GrayBackground
import com.example.tsdemoapp.ui.theme.GrayFontColor
import com.example.tsdemoapp.ui.theme.LatoFontFamily
import com.example.tsdemoapp.ui.theme.MontserratFontFamily
import com.example.tsdemoapp.ui.theme.SecondaryColor
import com.example.tsdemoapp.ui.theme.blackFont
import com.example.tsdemoapp.ui.theme.lightGray
import com.example.tsdemoapp.ui.theme.redFont
import com.example.tsdemoapp.viewmodel.ProfileViewModel


@Composable
fun VerifyAccountScreen(
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
                modifier = Modifier.fillMaxWidth()
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

                UserInfo(userProfile)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfo(userProfile: UserProfile?) {
    var firstName by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var monthlyIncome by remember { mutableStateOf("") }
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
            ProfileActionRow("Add Selfie Photo")
            BottomBorder()
            ProfileActionRow("Add Identification")
            BottomBorder()
            ProfileInputField("First Name", "Enter your first name")
            BottomBorder()
            ProfileInputField("Last Name", "Enter your last name")
            BottomBorder()
            ProfileInputField("Email", "Enter your email")
            BottomBorder()
            ProfileInputField("Date of Birth", "Select your birth")
            BottomBorder()
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
            modifier = Modifier,
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
                modifier = Modifier.size(32.dp)
                    .padding(start = 8.dp, end = 16.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInputField(label: String, placeholder: String) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = blackFont,
            fontWeight = FontWeight.Normal,
        )
        TextField(
            textStyle = TextStyle(color = Black, fontSize = 16.sp),
            value = text,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            onValueChange = {
                text = it
            },
            placeholder = { Text(placeholder) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GrayBackground,
                unfocusedBorderColor = GrayBackground
            ),
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(.5f),
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    VerifyAccountScreen(navController)
}