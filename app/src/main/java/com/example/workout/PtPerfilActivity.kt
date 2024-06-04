package com.example.workout

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.PtperfilBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class PtPerfilActivity : ComponentActivity() {

    private lateinit var binding: PtperfilBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PtperfilBinding.inflate(layoutInflater)
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
                    val espec = snap.child("espec").value.toString()
                    val espec1 = espec.split("-")[0].trim()
                    val espec2 = espec.split("-")[1].trim()
                    val espec3 = espec.split("-")[2].trim()
                    val email = snap.child("email").value.toString()
                    binding.ptnome.text=nome
                    binding.ptespec.text="$espec1\n$espec2\n$espec3"
                    binding.ptemail.text=email
                } else {
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@PtPerfilActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)
        binding.icon1.setOnClickListener {
            val intent = Intent(this@PtPerfilActivity, PtHomeActivity::class.java)
            startActivity(intent)
        }


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
    }
}