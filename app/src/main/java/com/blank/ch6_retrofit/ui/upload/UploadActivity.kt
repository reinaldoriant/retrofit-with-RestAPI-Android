package com.blank.ch6_retrofit.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.blank.ch6_retrofit.R
import com.blank.ch6_retrofit.data.remote.ApiModule
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File


class UploadActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var btnGetImg: Button
    lateinit var btnUpload: Button
    private var filePath: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val factory = UploadViewModel.Factory(ApiModule.service)
        val viewModel = ViewModelProvider(this, factory)[UploadViewModel::class.java]

        image = findViewById(R.id.image)
        btnGetImg = findViewById(R.id.btnGetImage)
        btnUpload = findViewById(R.id.btnUploadImg)

        btnGetImg.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }

        btnUpload.setOnClickListener {
            filePath?.let { it1 -> viewModel.upload("Username123", "emailku123@gmail.com", it1) }
        }

        viewModel.resultPost.observe(this) {
            Glide.with(this)
                .load(it.data.photo)
                .into(image)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                filePath = resultUri.toFile()
//                image.setImageUriAsync(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}