package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.StatsBinding


class StatsActivity : ComponentActivity() {

    private lateinit var binding: StatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StatsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            val intent = Intent(this@StatsActivity, RewardsActivity::class.java)
            startActivity(intent)
        }

        binding.icon1.setOnClickListener{
            val intent = Intent(this@StatsActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@StatsActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}