package com.example.tsdemoapp.ui

import com.example.tsdemoapp.ui.nav.NavHost
import com.example.tsdemoapp.ui.nav.Routes
import com.example.tsdemoapp.ui.nav.TopAppBar
import com.example.tsdemoapp.ui.theme.TSDemoAppTheme
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TSDemoApp(context: Context) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    TSDemoAppTheme(currentRoute = currentRoute) {

        val startDestination = Routes.Profile.name

        Scaffold(
            topBar = {
                TopAppBar(navController, scrollBehavior)
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { innerPadding ->
            NavHost(
                navHostController = navController,
                startDestination = startDestination,
                Modifier.padding(innerPadding),
            )
        }
    }

}