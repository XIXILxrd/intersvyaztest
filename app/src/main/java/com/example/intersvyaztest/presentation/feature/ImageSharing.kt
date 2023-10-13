package com.example.intersvyaztest.presentation.feature

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.example.itntersvyaztest.R
import java.io.File
import java.io.FileOutputStream

class ImageSharing(private val context: Context) {

    fun shareImage(imageView: ImageView) {
        val bitmap = imageView.drawable.toBitmap()

        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        val fileOutputStream = FileOutputStream(file)

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

            val resolvedActivity = context.packageManager.resolveActivity(
                shareIntent,
                0
            )

            if (resolvedActivity != null) {
                context.startActivity(
                    Intent.createChooser(
                        shareIntent,
                        context.getString(R.string.share_image_text)
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_suitable_applications_for_image_sharing_toast_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                context.getString(R.string.error_sharing_an_image_snackbar_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}