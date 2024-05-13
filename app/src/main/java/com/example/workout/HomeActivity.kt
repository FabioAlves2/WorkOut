package com.example.workout

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.HomeBinding

class HomeActivity : ComponentActivity() {

    private lateinit var binding: HomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}