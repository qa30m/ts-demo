package com.example.tsdemoapp.ui.nav


import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tsdemoapp.ui.components.Pending
import com.example.tsdemoapp.ui.screens.CameraScreen
import com.example.tsdemoapp.ui.screens.ProfileScreen
import com.example.tsdemoapp.ui.screens.SelfieInstructionScreen
import com.example.tsdemoapp.ui.screens.SelfieScreen
import com.example.tsdemoapp.viewmodel.MainViewModel
import com.github.skgmn.startactivityx.PermissionStatus
import kotlinx.coroutines.flow.Flow
import kotlin.getValue

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
            ProfileScreen(navHostController)
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
        composable(Routes.AddIdentification.name) {
            Pending()
        }
    }
}