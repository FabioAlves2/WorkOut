package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.ObjetivosBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class ObjetivosActivity : ComponentActivity() {

    private lateinit var binding: ObjetivosBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ObjetivosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        ref = database.getReference("Utilizadores")
        if (user != null) {
            ref.child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val snap = task.result
                    val peso = snap.child("peso").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $peso")
                    val altura = snap.child("altura").value.toString()
                    val ipeso = peso.toDouble()
                    val ialtura = altura.toDouble()
                    val imc = String.format("%.1f", calcularIMC(ipeso , ialtura))
                    Log.d(ContentValues.TAG, "Value is: $imc")
                    binding.textView.text="O seu IMC (Índice de Massa Corporal) é de $imc kg/m2."
                    val pesoIdeal = pesoIdeal(ialtura)
                    val pesoMinimo = String.format("%.1f", pesoIdeal.first)
                    val pesoMaximo = String.format("%.1f", pesoIdeal.second)
                    binding.textView2.text="Os valores normais encontra-se entre os 18.5 e 24.9 kg/m2. O seu peso ideal encontra-se entre $pesoMinimo e $pesoMaximo kg."
                }else{
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@ObjetivosActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val objetivo = resources.getStringArray(R.array.Objetivo)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,objetivo)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.button.setOnClickListener {
            val selecteditem = binding.spinner.selectedItemPosition
            if (user != null) {
                ref.child(user.uid).child("objetivo").setValue(objetivo[selecteditem])
            }
            val intent = Intent(this@ObjetivosActivity, EscolhaActivity::class.java)
            startActivity(intent)
        }

    }
    private fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }
    private fun pesoIdeal(altura: Double): Pair<Double, Double>{
        val pesoMinimo = 18.5 * (altura * altura)
        val pesoMaximo = 24.9 * (altura * altura)
        return Pair(pesoMinimo, pesoMaximo)
    }
}