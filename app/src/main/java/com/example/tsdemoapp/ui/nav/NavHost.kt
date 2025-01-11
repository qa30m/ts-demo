package com.example.tsdemoapp.ui.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tsdemoapp.ui.screens.EIdInstructionScreen
import com.example.tsdemoapp.ui.screens.EIdScanScreen
import com.example.tsdemoapp.ui.screens.ProfileScreen
import com.example.tsdemoapp.ui.screens.SelfieInstructionScreen
import com.example.tsdemoapp.ui.screens.SelfieScreen
import com.example.tsdemoapp.viewmodel.MainViewModel
import com.github.skgmn.startactivityx.PermissionStatus
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navHostController: NavHostController,
    startDestination : String,
    modifier: Modifier,
    viewModel: MainViewModel,
    permissionStatusFlow: Flow<PermissionStatus>,
    onRequestCameraPermission: () -> Unit,
    onTakePhoto: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Routes.Profile.name) {
            ProfileScreen(navHostController, viewModel)
        }
        composable(Routes.SelfieInstructions.name) {
            SelfieInstructionScreen(navHostController)
        }
        composable(Routes.TakeSelfie.name) {
            SelfieScreen(
                navHostController,
                viewModel,
                permissionStatusFlow,
                onRequestCameraPermission,
                onTakePhoto
            )
        }
        composable(Routes.AddIdentificationInstruction.name) {
            EIdInstructionScreen(navHostController)
        }
        composable(Routes.AddIdentification.name) {
            EIdScanScreen(
                navHostController,
                viewModel,
                permissionStatusFlow,
                onRequestCameraPermission,
                onTakePhoto
            )
        }
    }
}