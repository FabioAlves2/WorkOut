package com.example.workout

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.EscolhaptBinding
import com.example.workout.databinding.ObjetivosBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class EscolhaActivity : ComponentActivity() {

    private lateinit var binding: EscolhaptBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var uref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EscolhaptBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        ref = database.getReference("Personal")
        uref = database.getReference("Utilizadores")

        definirPT("sK8uE8r0GVV9lIy83kXU7nC1NnA3",binding.Nome1,binding.habli1)
        setContentView(binding.root)

        binding.pt1.setOnClickListener{
            if (user != null) {
                Log.d(TAG, "O utilizador ${user.uid} selecionou o PT 1.")
                uref.child(user.uid).child("Pt").setValue("sK8uE8r0GVV9lIy83kXU7nC1NnA3")
                definircliente("sK8uE8r0GVV9lIy83kXU7nC1NnA3",user.uid)
            }
            val intent = Intent(this@EscolhaActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            if (user != null) {
                uref.child(user.uid).child("Pt").setValue(null)
            }
            val intent = Intent(this@EscolhaActivity, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun definirPT(pt: String, nomeView: TextView, habliView: TextView) {
        ref.child(pt).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snap = task.result
                val nome = snap.child("nome").value.toString()
                Log.d(ContentValues.TAG, "Value is: $nome")
                val espec = snap.child("espec").value.toString()
                val espec1 = espec.split("-")[0].trim()
                val espec2 = espec.split("-")[1].trim()
                val espec3 = espec.split("-")[2].trim()
                nomeView.text = nome
                habliView.text = "$espec1 \n$espec2 \n$espec3"
            } else {
                Log.w(ContentValues.TAG, "Failed to read value.")
                Toast.makeText(this@EscolhaActivity, "Erro!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun definircliente(id_pt: String, id_user: String) {
        ref.child(id_pt).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snap = task.result
                val nome = snap.child("nome").value.toString()
                Log.d(ContentValues.TAG, "Value is: $nome")
                val clientes = snap.child("clientes").value.toString()
                if (clientes!="null"){
                    val add = "$clientes-$id_user"
                    ref.child(id_pt).child("clientes").setValue(add)
                }else{
                    ref.child(id_pt).child("clientes").setValue(id_user)
                }

            } else {
                Log.w(ContentValues.TAG, "Failed to read value.")
                Toast.makeText(this@EscolhaActivity, "Erro!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}