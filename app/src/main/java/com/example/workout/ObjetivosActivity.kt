package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.ObjetivosBinding

class ObjetivosActivity : ComponentActivity() {

    private lateinit var binding: ObjetivosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ObjetivosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this@ObjetivosActivity, EscolhaActivity::class.java)
            startActivity(intent)
        }

    }
}