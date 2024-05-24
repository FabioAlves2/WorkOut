package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.CommunityBinding
import com.example.workout.databinding.StatsBinding

class ChatActivity : ComponentActivity() {

    private lateinit var binding: CommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.tab2.setOnClickListener {
            val intent = Intent(this@ChatActivity, DesafioActivity::class.java)
            startActivity(intent)
        }

        binding.icon1.setOnClickListener{
            val intent = Intent(this@ChatActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@ChatActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}