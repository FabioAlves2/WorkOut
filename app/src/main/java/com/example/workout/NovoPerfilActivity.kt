package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.NovoperfilBinding

class NovoPerfilActivity : ComponentActivity() {

    private lateinit var binding: NovoperfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NovoperfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this@NovoPerfilActivity, ObjetivosActivity::class.java)
            startActivity(intent)
        }

    }
}