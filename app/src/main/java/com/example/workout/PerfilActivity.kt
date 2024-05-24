package com.example.workout

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.PerfilBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class PerfilActivity : ComponentActivity() {

    private lateinit var binding: PerfilBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var uref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PerfilBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        ref = database.getReference("Utilizadores")
        if (user != null) {
            ref.child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val snap = task.result
                    val nome = snap.child("nome").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $nome")
                    val utilizador = snap.child("user").value.toString()
                    val email = snap.child("email").value.toString()
                    val data = snap.child("nasc").value.toString()
                    val peso = snap.child("peso").value.toString()
                    val altura = snap.child("altura").value.toString()
                    val obj = snap.child("objetivo").value.toString()
                    binding.unome.text=nome
                    binding.utilizador.text="@$utilizador"
                    binding.email.text=email
                    binding.udata.text=data
                    binding.upeso.text="$peso kg"
                    binding.ualtura.text="$altura m"
                    binding.uobjetivo.text=obj

                }else{
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@PerfilActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)


        binding.logout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Quer mesmo fazer logout da sua conta?")
            builder.setPositiveButton("Sim") { _, _ ->
                // Navigate to MainActivity
                Firebase.auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            builder.setNegativeButton("Cancelar") { _, _ ->
                // Do nothing, simply dismiss the dialog
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.back.setOnClickListener {
            val intent = Intent(this@PerfilActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.icon1.setOnClickListener{
            val intent = Intent(this@PerfilActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon4.setOnClickListener {
            val intent = Intent(this@PerfilActivity, StatsActivity::class.java)
            startActivity(intent)
        }

    }
}