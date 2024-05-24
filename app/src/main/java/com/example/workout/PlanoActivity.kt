package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.PlanoBinding


class PlanoActivity : ComponentActivity() {

    private lateinit var binding: PlanoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlanoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.comecar.setOnClickListener {
            val intent = Intent(this@PlanoActivity, ExercicioActivity::class.java)
            startActivity(intent)
        }

        binding.icon1.setOnClickListener{
            val intent = Intent(this@PlanoActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@PlanoActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}