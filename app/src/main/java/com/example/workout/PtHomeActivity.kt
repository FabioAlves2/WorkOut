package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.PthomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class PtHomeActivity : ComponentActivity() {

    private lateinit var binding: PthomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PthomeBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        ref = database.getReference("Personal")
        if (user != null) {
            ref.child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snap = task.result
                    val nome = snap.child("nome").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $nome")
                    binding.olamsg.text="Olá $nome"
                    val clientes = snap.child("clientes").value.toString()
                    if (clientes.isNotEmpty()) {
                        val clienteList = clientes.split("-").map { it.trim() }
                        var i=1
                        for (cliente in clienteList){
                            if (i==1){
                                fillinfo(cliente,binding.client1,binding.descr1)
                            }else if (i==2)
                            i += 1
                            //completar
                        }
                    } else {
                        //completar
                    }

                } else {
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@PtHomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)

        binding.icon5.setOnClickListener {
            val intent = Intent(this@PtHomeActivity, PerfilActivity::class.java)
            startActivity(intent)
        }


    }
    private fun fillinfo(pt: String, nome: TextView, cl_info: TextView) {

    }
}