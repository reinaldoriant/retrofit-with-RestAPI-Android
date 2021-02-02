package com.blank.ch6_retrofit.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blank.ch6_retrofit.R
import com.blank.ch6_retrofit.data.remote.ApiModule

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val factory = MainViewModel.Factory(ApiModule.service)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val btnPost = findViewById<Button>(R.id.btnGetPost)
        val btn = findViewById<Button>(R.id.btn)

        btnPost.setOnClickListener {
            viewModel.getPost()
        }

        btn.setOnClickListener {
            viewModel.munculLagiKamu()
        }

        viewModel.resultPost.observe(this, {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}