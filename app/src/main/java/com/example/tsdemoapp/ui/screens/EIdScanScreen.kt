package com.example.tsdemoapp.ui.screens

import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tsdemoapp.ui.components.DefaultButton
import com.example.tsdemoapp.ui.components.InstructionItem
import com.example.tsdemoapp.ui.components.PrimaryButton
import com.example.tsdemoapp.ui.nav.Routes
import com.example.tsdemoapp.ui.theme.MontserratFontFamily
import com.example.tsdemoapp.ui.theme.lightGray
import com.example.tsdemoapp.viewmodel.MainViewModel
import com.example.tsdemoapp.viewmodel.MainViewModel.CaptureType
import com.github.skgmn.cameraxx.CameraPreview
import com.github.skgmn.cameraxx.FocusMeteringState
import com.github.skgmn.cameraxx.TorchState
import com.github.skgmn.cameraxx.ZoomState
import com.github.skgmn.startactivityx.PermissionStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EIdScanScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    permissionStatusFlow: Flow<PermissionStatus>,
    onRequestCameraPermission: () -> Unit,
    onTakePhoto: () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) } // State to manage dropdown visibility
    var options = listOf("E-Tazkira", "Passport", "NID")
    var selectedText by remember { mutableStateOf(options[0]) } // State to track selected option
    val capturedBitmap by remember { viewModel.capturedEId }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8)) // Light background color
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Spacer from the top
        Spacer(modifier = Modifier.height(30.dp))

        // Title
        Text(
            text = "Select Confirmation Method",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = MontserratFontFamily
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Please complete the process below",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = lightGray
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Document Type", modifier = Modifier.weight(1f))
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
                                    expanded = false
                                },
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = lightGray
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Please take a picture from back of your E-Tazkira to proceed.",
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                // Spacer from the top
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                ) {
                    CameraScreen(
                        navController,
                        viewModel,
                        permissionStatusFlow,
                        onRequestCameraPermission,
                        onTakePhoto
                    )
                }
            }
        }

        // show when photo is taken
        if(viewModel.capturedEId.value != null) {

            // Spacer for buttons to have some margin
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(text = "Save") {
                    navController.navigate(Routes.Profile.name) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                DefaultButton(text = "Try again") {
                    viewModel.capturedEId.value = null
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun CameraScreen(
    navController: NavController,
    viewModel: MainViewModel,
    permissionStatusFlow: Flow<PermissionStatus>,
    onRequestCameraPermission: () -> Unit,
    onTakePhoto: () -> Unit
) {
    val permissionStatus by permissionStatusFlow.collectAsState(null)
    val permissionInitiallyRequested by viewModel.permissionsInitiallyRequestedState.collectAsState(
        false
    )
    val savingPhoto by viewModel.savingPhotoState.collectAsState()

    if (viewModel.capturedEId.value == null && permissionStatus?.granted == true) {
        CameraLayer(viewModel, onTakePhoto)
    }
    else {
        PhotoPreview(viewModel)
    }

    if (permissionStatus?.denied == true) {
        PermissionLayer(onRequestCameraPermission)
    }
    if (savingPhoto) {
        SavingProgress()
    }
}

@Composable
private fun CameraLayer(
    mainViewModel: MainViewModel,
    onTakePhoto: () -> Unit
) {
    val zoomState = remember { ZoomState(pinchZoomEnabled = true) }
    val torchState = remember { TorchState() }
    val focusMeteringState = remember { FocusMeteringState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .background(Color(0xff000000))
    ) {
        CameraPreview(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            imageCapture = mainViewModel.imageCapture,
            zoomState = zoomState,
            torchState = torchState,
            focusMeteringState = focusMeteringState,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
            scaleType = PreviewView.ScaleType.FIT_CENTER
        )
        ZoomRatioText(zoomState)
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            onClick = {
//                onTakePhoto()
                coroutineScope.launch {
                    mainViewModel.savingPhotoState.value = true
                    mainViewModel.takePhoto(MainViewModel.CaptureType.EID)
                    mainViewModel.savingPhotoState.value = false
                }
            }
        ) {
            Text(text = "Take Photo")
        }
        TorchToggleButton(torchState)
    }
}

@Composable
private fun PhotoPreview(viewModel: MainViewModel) {
    val bitmap = viewModel.capturedEId.value

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Captured Photo",
            modifier = Modifier
                .fillMaxWidth(), // Adjust this to fit the desired aspect ratio
            contentScale = ContentScale.Crop
        )
    } else {
        Text(
            text = "No photo captured yet",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BoxScope.TorchToggleButton(torchState: TorchState) {
    if (torchState.hasFlashUnit == true) {
        torchState.isOn?.let { isOn ->
            Button(
                modifier = Modifier.Companion
                    .align(Alignment.TopEnd)
                    .offset((-8).dp, 8.dp),
                onClick = { torchState.isOn = !isOn }
            ) {
                Text(if (isOn) "Off" else "On")
            }
        }
    }
}

@Composable
private fun BoxScope.ZoomRatioText(zoomState: ZoomState) {
    if (zoomState.pinchZoomInProgress && zoomState.ratio != null) {
        Text(
            text = "%.1fx".format(zoomState.ratio),
            color = Color(0xffffffff),
            fontSize = 36.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun SavingProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xA0000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Saving Photo",
                color = Color(0xffffffff)
            )
        }
    }
}

@Composable
private fun PermissionLayer(onRequestCameraPermission: () -> Unit) {
    val currentOnRequestCameraPermission by rememberUpdatedState(onRequestCameraPermission)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff000000))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Permission Required",
                color = colorResource(android.R.color.white),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { currentOnRequestCameraPermission() }) {
                Text(
                    text = "Grant Permission",
                    fontSize = 13.sp
                )
            }
        }
    }
}