package com.example.tsdemoapp.ui.nav

import androidx.navigation.NavController

fun navigateWithPopUpTo(navController: NavController, route: String) {
    navController.navigate(route) {
//        // Pop up to the start destination of the graph to
//        // avoid building up a large stack of destinations
//        // on the back stack as users select items
//        popUpTo(navController.graph.findStartDestination().id) {
//            saveState = true
//        }
//        // Avoid multiple copies of the same destination when
//        // reselecting the same item
//        launchSingleTop = true
//        // Restore state when reselecting a previously selected item
//        restoreState = true
        popUpTo(route) { inclusive = true }
    }
}