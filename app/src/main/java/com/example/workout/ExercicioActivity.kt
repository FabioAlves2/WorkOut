package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.Exercicio1Binding

class ExercicioActivity : ComponentActivity() {

    private lateinit var binding: Exercicio1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Exercicio1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.avancar.setOnClickListener {
            val intent = Intent(this@ExercicioActivity, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}