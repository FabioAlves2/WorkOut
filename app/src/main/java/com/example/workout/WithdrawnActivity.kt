package com.example.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.workout.databinding.WithdrawnBinding
import kotlin.random.Random

class WithdrawnActivity : ComponentActivity() {

    private lateinit var binding: WithdrawnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WithdrawnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val codigo=List(13){ Random.nextInt(0,9)}
        val mensagem1 = codigo.joinToString(separator = "")
        binding.codigo.text=mensagem1


        binding.icon1.setOnClickListener{
            val intent = Intent(this@WithdrawnActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@WithdrawnActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}