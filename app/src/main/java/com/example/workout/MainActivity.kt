package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workout.databinding.HomeBinding
import com.example.workout.databinding.LoginBinding
import com.example.workout.ui.theme.WorkOutTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.butLogin.setOnClickListener {
            val intent = Intent(this@MainActivity, NovoPerfilActivity::class.java)
            startActivity(intent)
        }

    }
}
