package com.example.tsdemoapp.ui.nav


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tsdemoapp.ui.screens.ProfileScreen
import com.example.tsdemoapp.ui.screens.VerifyAccountScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navHostController: NavHostController,
    startDestination : String,
    modifier: Modifier,
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Routes.VerifyAccount.name) {
            VerifyAccountScreen(navHostController)
//            ProfileScreen()
        }
    }
}