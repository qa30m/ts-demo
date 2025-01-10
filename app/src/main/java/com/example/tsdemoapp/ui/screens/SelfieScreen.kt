package com.example.tsdemoapp.ui.screens


import androidx.camera.core.CameraSelector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tsdemoapp.ui.components.DefaultButton
import com.example.tsdemoapp.ui.components.InstructionItem
import com.example.tsdemoapp.ui.components.PrimaryButton
import com.example.tsdemoapp.ui.nav.Routes
import com.example.tsdemoapp.viewmodel.MainViewModel
import com.github.skgmn.cameraxx.CameraPreview
import com.github.skgmn.cameraxx.FocusMeteringState
import com.github.skgmn.cameraxx.TorchState
import com.github.skgmn.cameraxx.ZoomState
import com.github.skgmn.startactivityx.PermissionStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfieScreen(
    navController: NavController,
    viewModel: MainViewModel,
    permissionStatusFlow: Flow<PermissionStatus>,
    onRequestCameraPermission: () -> Unit,
    onTakePhoto: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)) // Light background color
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Spacer to center the content vertically
        Spacer(modifier = Modifier.height(30.dp))

        // Title
        Text(
            text = "Selfie",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(300.dp)
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

        Spacer(modifier = Modifier.height(64.dp))

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
                navController.navigate(Routes.Profile.name) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            }
            DefaultButton(text = "Try again") {
                viewModel.capturedBitmap.value = null
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CameraScreen(
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

    if (viewModel.capturedBitmap.value == null && permissionStatus?.granted == true) {
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

    Box(
        modifier = Modifier
            .background(Color(0xff000000))
            .fillMaxWidth().height(300.dp)
    ) {
        CameraPreview(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            imageCapture = mainViewModel.imageCapture,
            zoomState = zoomState,
            torchState = torchState,
            focusMeteringState = focusMeteringState,
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        )
        ZoomRatioText(zoomState)
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            onClick = { onTakePhoto() }
        ) {
            Text(text = "Take Photo")
        }
        TorchToggleButton(torchState)
    }
}

@Composable
fun PhotoPreview(viewModel: MainViewModel) {
    val bitmap = viewModel.capturedBitmap.value

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Captured Photo",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // Adjust this to fit the desired aspect ratio
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
fun BoxScope.ZoomRatioText(zoomState: ZoomState) {
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
