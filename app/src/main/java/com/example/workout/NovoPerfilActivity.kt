package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.NovoperfilBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class NovoPerfilActivity : ComponentActivity() {

    private lateinit var binding: NovoperfilBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NovoperfilBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        ref = database.getReference("Utilizadores")
        setContentView(binding.root)

        val dados = intent.extras
        val email = dados?.getString("email")
        binding.email.text = email


        binding.button.setOnClickListener {
            //guardar dados na base de dados
            val peso = binding.peso.text.toString().trim().toDouble()
            val altura =binding.altura.text.toString().trim().toDouble()
            var completo = true

            if (user != null) {
                ref.child(user.uid).child("nome").setValue(binding.nome.text.toString().trim())
                ref.child(user.uid).child("user").setValue(binding.user.text.toString().trim())
                ref.child(user.uid).child("email").setValue(binding.email.text.toString().trim())
                ref.child(user.uid).child("nasc").setValue(binding.data.text.toString().trim())
                if(peso > 10){
                    ref.child(user.uid).child("peso").setValue(binding.peso.text.toString().trim())
                }
                else{
                    completo = false
                    Toast.makeText(this@NovoPerfilActivity, "O seu peso está incorreto!", Toast.LENGTH_SHORT).show()
                }
                if (altura > 0){
                    ref.child(user.uid).child("altura").setValue(binding.altura.text.toString().trim())
                }else{
                    completo = false
                    Toast.makeText(this@NovoPerfilActivity, "A sua altura está incorreta!", Toast.LENGTH_SHORT).show()
                }
                if (binding.genero.text.toString().trim() == "Feminino" || binding.genero.text.toString().trim() == "Masculino"){
                    ref.child(user.uid).child("genero").setValue(binding.genero.text.toString().trim())
                }else{
                    completo = false
                    Toast.makeText(this@NovoPerfilActivity, "Verifique o seu género!", Toast.LENGTH_SHORT).show()
                }
            }
            if(completo){
                val intent = Intent(this@NovoPerfilActivity, ObjetivosActivity::class.java)
                startActivity(intent)
            }
        }

    }
}