package com.blank.ch6_retrofit.ui.regis

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blank.ch6_retrofit.R
import com.blank.ch6_retrofit.data.model.RequestLogin
import com.blank.ch6_retrofit.data.model.RequestRegis
import com.blank.ch6_retrofit.data.remote.ApiModule

class RegisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)
        val factory = RegisViewModel.Factory(ApiModule.service)
        val viewModel = ViewModelProvider(this, factory)[RegisViewModel::class.java]

        val req = RequestRegis("ghozicoy@gmail.com", "Ghozi123123", "ghoziMan")
        val reqLogin = RequestLogin("ghozicoy@gmail.com", "Ghozi12312")

        findViewById<Button>(R.id.button2).setOnClickListener {
            viewModel.login(reqLogin)
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.register(req)
        }

        viewModel.resultPost.observe(this) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        viewModel.resultMe.observe(this) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        viewModel.resultLogin.observe(this) {
            viewModel.showMeData("Bearer ".plus(it.data.token))
        }
    }
}