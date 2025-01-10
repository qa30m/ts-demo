package com.example.tsdemoapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val imageCapture = ImageCapture.Builder().build()

    val permissionsInitiallyRequestedState = MutableStateFlow(false)
    val savingPhotoState = MutableStateFlow(false)
    val capturedBitmap = mutableStateOf<Bitmap?>(null)

//    fun takePhotoAsync(): Deferred<Uri?> {
//        return viewModelScope.async {
////            val outputOptions = ImageCapture.OutputFileOptions.Builder(
////                getApplication<Application>().contentResolver,
////                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
////                ContentValues()
////            ).build()
////            imageCapture.takePicture(outputOptions).savedUri
//            imageCapture.takePicture(
//                executor = Executors.newSingleThreadExecutor(),
//                callback = object : ImageCapture.OnImageCapturedCallback() {
//                    override fun onCaptureSuccess(imageProxy: ImageProxy) {
//                        val buffer = imageProxy.planes[0].buffer
//                        val bytes = ByteArray(buffer.remaining())
//                        buffer.get(bytes)
//                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                        imageProxy.close()
//                        bitmap
//                    }
//
//                    override fun onError(exception: ImageCaptureException) {
//                        super.onError(exception)
//                    }
//                }
//            )
//        }
//    }

    suspend fun takePhoto() {
        capturedBitmap.value = suspendCoroutine { continuation ->
            imageCapture.takePicture(
                Executors.newSingleThreadExecutor(),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(imageProxy: ImageProxy) {
                        try {
                            val buffer = imageProxy.planes[0].buffer
                            val bytes = ByteArray(buffer.remaining())
                            buffer.get(bytes)
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            val rotationDegrees = imageProxy.imageInfo.rotationDegrees

                            // Rotate the bitmap
                            val adjustedBitmap = adjustBitmap(bitmap, rotationDegrees)
                            imageProxy.close()
                            continuation.resume(adjustedBitmap)
                        } catch (e: Exception) {
                            continuation.resumeWith(Result.failure(e))
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resumeWith(Result.failure(exception))
                    }
                }
            )
        }
    }

    private fun newImageCapture() = ImageCapture.Builder().build()

    private fun adjustBitmap(bitmap: Bitmap, rotationDegrees: Int, isFrontCamera: Boolean = true): Bitmap {
        val matrix = Matrix()

        // Apply rotation
        matrix.postRotate(rotationDegrees.toFloat())

        // Apply horizontal flip if using the front camera
        if (isFrontCamera) {
            matrix.postScale(-1f, 1f) // Flip horizontally
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}