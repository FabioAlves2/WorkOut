package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.PlanofimBinding
import com.example.workout.databinding.StatsBinding

class PlanoFimActivity : ComponentActivity() {

    private lateinit var binding: PlanofimBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlanofimBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recolher.setOnClickListener {
            val intent = Intent(this@PlanoFimActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.icon1.setOnClickListener{
            val intent = Intent(this@PlanoFimActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.icon2.setOnClickListener {

        }
        binding.icon3.setOnClickListener {
            val intent = Intent(this@PlanoFimActivity, ChatActivity::class.java)
            startActivity(intent)
        }
        binding.icon4.setOnClickListener {
            val intent = Intent(this@PlanoFimActivity, StatsActivity::class.java)
            startActivity(intent)
        }
        binding.icon5.setOnClickListener {
            val intent = Intent(this@PlanoFimActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}