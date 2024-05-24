package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.DesafioBinding
import com.example.workout.databinding.StatsBinding

class DesafioActivity : ComponentActivity() {

    private lateinit var binding: DesafioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DesafioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tab1.setOnClickListener {
            val intent = Intent(this@DesafioActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        binding.icon1.setOnClickListener{
            val intent = Intent(this@DesafioActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@DesafioActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}