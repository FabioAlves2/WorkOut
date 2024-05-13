package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.EscolhaptBinding
import com.example.workout.databinding.ObjetivosBinding

class EscolhaActivity : ComponentActivity() {

    private lateinit var binding: EscolhaptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EscolhaptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this@EscolhaActivity, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}