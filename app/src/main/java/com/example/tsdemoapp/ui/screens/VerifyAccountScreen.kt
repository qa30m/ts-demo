package com.example.tsdemoapp.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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
import com.example.tsdemoapp.model.UserProfile
import com.example.tsdemoapp.preferences.UserPreferences
import com.example.tsdemoapp.ui.theme.FontColorBlack
import com.example.tsdemoapp.ui.theme.FontColorBlack2
import com.example.tsdemoapp.ui.theme.GrayBackground
import com.example.tsdemoapp.ui.theme.GrayFontColor
import com.example.tsdemoapp.ui.theme.LatoFontFamily
import com.example.tsdemoapp.ui.theme.PoppinsFontFamily
import com.example.tsdemoapp.ui.theme.SecondaryColor
import com.example.tsdemoapp.ui.theme.accentColor
import com.example.tsdemoapp.ui.theme.blackFont
import com.example.tsdemoapp.ui.theme.lightGray
import com.example.tsdemoapp.ui.theme.lightGrayFont
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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontFamily = LatoFontFamily,
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row (
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = LatoFontFamily,
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                    Box(
                        modifier = Modifier.height(3.dp)
                            .fillMaxWidth()
                            .background(blackFont)
                            .padding(bottom = 4.dp)
                    )
                }
            }
        }
        item {
            UserInfo(userProfile)
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
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 32.dp),
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
//            add selfie
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Add Selfie Photo",
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
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "",
                        tint = redFont,
                        modifier = Modifier.size(16.dp)
                            .padding(end = 16.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(blackFont)
                    .height(1.dp)
            )
//            add identification
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Add Identification",
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
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "",
                        tint = redFont,
                        modifier = Modifier.size(16.dp)
                            .padding(end = 16.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(blackFont)
                    .height(1.dp)
            )
//            first name
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "First Name",
                    fontSize = 16.sp,
                    color = blackFont,
                    fontWeight = FontWeight.Normal,
                )
                TextField(
                    textStyle = TextStyle(color = Black, fontSize = 16.sp),
                    value = firstName,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = {
                        firstName = it
                    },
//                    shape = RoundedCornerShape(10.dp),
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(blackFont)
                    .height(1.dp)
            )
        }
    }
}

@Composable
fun InfoItem(
    name: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(8.dp)
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            color = SecondaryColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(.5f),
        )
        Text(
            text = name,
            fontSize = 14.sp,
            color = GrayFontColor,
            fontWeight = FontWeight(500),
            modifier = Modifier.fillMaxWidth(.5f),
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    VerifyAccountScreen(navController)
}