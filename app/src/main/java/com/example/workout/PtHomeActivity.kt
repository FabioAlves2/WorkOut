package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
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
    private lateinit var uref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PthomeBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        uref = database.getReference("Utilizadores")
        ref = database.getReference("Personal")
        var cliente1 = ""
        var cliente2 = ""
        var cliente3 = ""
        if (user != null) {
            ref.child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snap = task.result
                    val nome = snap.child("nome").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $nome")
                    binding.olamsg.text="Olá $nome"
                    val clientes = snap.child("clientes").value.toString()
                    if (clientes.isNotEmpty()) {
                        Log.w(ContentValues.TAG, "TEM CLIENTES ASSOCIADOS")
                        val clienteList = clientes.split("-").map { it.trim() }
                        var i=1
                        for (cliente in clienteList){
                            if (i==1){
                                binding.cliente1.visibility=VISIBLE
                                Log.w(ContentValues.TAG, "$cliente load")
                                fillinfo(cliente,binding.client1,binding.descr1)
                                cliente1 = cliente
                            }else if (i==2){
                                binding.cliente2.visibility=VISIBLE
                                fillinfo(cliente,binding.client2,binding.descr2)
                                cliente2 = cliente
                            }else if (i==3){
                                binding.cliente3.visibility=VISIBLE
                                fillinfo(cliente,binding.client3,binding.descr3)
                                cliente3 = cliente
                            }
                            i+=1

                            //completar
                        }
                    } else {
                        Log.w(ContentValues.TAG, "MIAUUUUUUUU.")
                    }

                } else {
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@PtHomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.cliente1.setOnClickListener {
            val intent = Intent(this@PtHomeActivity, ClienteActivity::class.java)
            intent.putExtra("cliente",cliente1)
            startActivity(intent)
        }
        binding.cliente2.setOnClickListener {
            val intent = Intent(this@PtHomeActivity, ClienteActivity::class.java)
            intent.putExtra("cliente",cliente2)
            startActivity(intent)
        }

        setContentView(binding.root)

        binding.icon5.setOnClickListener {
            val intent = Intent(this@PtHomeActivity, PtPerfilActivity::class.java)
            startActivity(intent)
        }


    }
    private fun fillinfo(cliente: String, nome: TextView, cl_info: TextView) {
        uref.child(cliente).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snap = task.result
                val clnome = snap.child("nome").value.toString()
                Log.d(ContentValues.TAG, "Value is: $clnome")
                val clobjetivo = snap.child("objetivo").value.toString()
                val clpeso = snap.child("peso").value.toString()
                val claltura = snap.child("altura").value.toString()
                nome.text=clnome
                cl_info.text="Objetivo: $clobjetivo\nAltura: $claltura | Peso: $clpeso"
            } else {
                Log.w(ContentValues.TAG, "Failed to read value.")
                Toast.makeText(this@PtHomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}