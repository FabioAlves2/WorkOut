package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.marginEnd
import com.example.workout.databinding.HomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class HomeActivity : ComponentActivity() {

    private lateinit var binding: HomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var uref: DatabaseReference
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        uref = database.getReference("Utilizadores")
        ref = database.getReference("Personal")

        if (user != null) {
            uref.child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val snap = task.result
                    val unome = snap.child("nome").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $unome")
                    binding.olamsg.text="Olá $unome!"
                    val pt = snap.child("Pt").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $pt")
                    ref.child(pt).get().addOnCompleteListener { task1 ->
                        if (task1.isSuccessful){
                            val snap1 = task1.result
                            val nome = snap1.child("nome").value.toString()
                            val img = snap1.child("img").value
                            Log.d(ContentValues.TAG, "Value is: $nome")
                            if (nome!="null"){
                                binding.topTextView.text=nome
                                binding.bottomTextView.text="Está disponível para esclarecer dúvidas!"
                                if (img == "pt1"){
                                    binding.centeredImageView.setImageResource(R.drawable.pt1)
                                }else if (img == "pt2"){
                                    binding.centeredImageView.setImageResource(R.drawable.pt2)
                                }else{
                                    binding.centeredImageView.setImageResource(R.drawable.pt3)
                                }

                            }else{
                                binding.topTextView.text=""
                                binding.bottomTextView.text = "Selecione um dos nossos Personal Tariners para ter um acompanhamento personalizado."
                            }
                        }else{
                            Log.w(ContentValues.TAG, "Failed to read value.")
                            Toast.makeText(this@HomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
                        }
                    }


                }else{
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@HomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)

        binding.plano.setOnClickListener {
            val intent = Intent(this@HomeActivity, PlanoActivity::class.java)
            startActivity(intent)
        }

        binding.icon3.setOnClickListener {
            val intent = Intent(this@HomeActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        binding.icon4.setOnClickListener {
            val intent = Intent(this@HomeActivity, StatsActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@HomeActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
    }
}