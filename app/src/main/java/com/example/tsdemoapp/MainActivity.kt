package com.example.tsdemoapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.tsdemoapp.ui.TSDemoApp
import com.example.tsdemoapp.viewmodel.MainViewModel
import com.github.skgmn.startactivityx.*
import com.github.skgmn.startactivityx.PermissionRequest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDemoApp(
                this,
                viewModel,
                listenPermissionStatus(Manifest.permission.CAMERA),
                onRequestCameraPermission = {
                    lifecycleScope.launch {
                        requestCameraPermission()
                    }
                },
                onTakePhoto = {
                    lifecycleScope.launch {
                        takePhoto()
                    }
                }
            )
        }
    }

//    private suspend fun takePhoto() {
//        viewModel.savingPhotoState.value = true
//        try {
//            whenStarted {
//                viewModel.takePhotoAsync().await()?.let { uri ->
//                    try {
////                        val intent = Intent(Intent.ACTION_VIEW, uri)
////                        startActivityForResult(intent)
//                    } catch (e: ActivityNotFoundException) {
//                        Toast
//                            .makeText(
//                                baseContext,
//                                "Photo Saved",
//                                Toast.LENGTH_SHORT
//                            )
//                            .show()
//                    }
//                }
//            }
//        } finally {
//            viewModel.savingPhotoState.value = false
//        }
//    }

    private suspend fun takePhoto() {
        viewModel.savingPhotoState.value = true
        try {
            whenStarted {
                val bitmap = viewModel.takePhoto()
                if (bitmap != null) {
                    // Handle the bitmap (e.g., save it, display it, etc.)
//                    Toast.makeText(
//                        baseContext,
//                        "Photo Captured Successfully",
//                        Toast.LENGTH_SHORT
//                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Failed to Capture Photo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                baseContext,
                "Error: ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            viewModel.savingPhotoState.value = false
        }
    }

    private suspend fun scanEId() {
        viewModel.savingPhotoState.value = true
        try {
            whenStarted {
                val bitmap = viewModel.takePhoto()
                if (bitmap != null) {
                    // Handle the bitmap (e.g., save it, display it, etc.)
                    Toast.makeText(
                        baseContext,
                        "Photo Captured Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Failed to Capture Photo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                baseContext,
                "Error: ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            viewModel.savingPhotoState.value = false
        }
    }

    private suspend fun requestCameraPermission() {
        val permissionRequest = PermissionRequest(PERMISSIONS, true)
        requestPermissions(permissionRequest)
    }

    companion object {
        private val PERMISSIONS = listOf(
            android.Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}