package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.ClienteBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class ClienteActivity : ComponentActivity() {

    private lateinit var binding: ClienteBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var uref: DatabaseReference
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClienteBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        uref = database.getReference("Utilizadores")
        ref = database.getReference("Personal")
        val dados = intent.extras
        val id = dados?.getString("cliente")
        if (id != null) {
            uref.child(id).get().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val snap = task.result
                    val unome = snap.child("nome").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $unome")
                    binding.clientename.text=unome
                    val ubirth = snap.child("nasc").value.toString()
                    binding.udata.text=ubirth
                    val peso = snap.child("peso").value.toString()
                    binding.upeso.text=peso
                    val altura = snap.child("altura").value.toString()
                    binding.ualtura.text=altura
                    val objetivo = snap.child("objetivo").value.toString()
                    binding.uobjetivo.text=objetivo
                }else{
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@ClienteActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.adicionar.setOnClickListener {
            val exercicio = binding.exercicio.text.toString().trim()
            val series = binding.series.text.toString().trim()
            val reps = binding.repeticao.text.toString().trim()
            val plano = binding.planoadd.text.toString().trim()
            binding.planoadd.text="$plano\n$exercicio - $series series - $reps repetições"
            if (id != null) {
                uref.child(id).child("plano").child(exercicio).child("nome").setValue(exercicio)
                uref.child(id).child("plano").child(exercicio).child("series").setValue(series)
                uref.child(id).child("plano").child(exercicio).child("reps").setValue(reps)
            }
            binding.exercicio.text.clear()
            binding.series.text.clear()
            binding.repeticao.text.clear()
        }
        binding.icon1.setOnClickListener{
            val intent = Intent(this@ClienteActivity, PtHomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@ClienteActivity, PtPerfilActivity::class.java)
            startActivity(intent)
        }




        setContentView(binding.root)



    }
}