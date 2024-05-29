package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.PlanoBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database


class PlanoActivity : ComponentActivity() {

    private lateinit var binding: PlanoBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var uref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlanoBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        uref = database.getReference("Utilizadores")

        if (user != null) {
            uref.child(user.uid).child("plano").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snap = task.result
                    val planViews = arrayOf(
                        Triple(binding.nome1, binding.serie1, binding.reps1),
                        Triple(binding.nome2, binding.serie2, binding.reps2),
                        Triple(binding.nome3, binding.serie3, binding.reps3),
                        Triple(binding.nome4, binding.serie4, binding.reps4),
                        Triple(binding.nome5, binding.serie5, binding.reps5),
                        Triple(binding.nome6, binding.serie6, binding.reps6)
                    )

                    val planIterator = snap.children.iterator()
                    for (i in planViews.indices) {
                        if (planIterator.hasNext()) {
                            val planSnapshot = planIterator.next()
                            val enome = planSnapshot.child("nome").value.toString()
                            val eseries = planSnapshot.child("series").value.toString()
                            val ereps = planSnapshot.child("reps").value.toString()
                            val (exView, serieView, repsView) = planViews[i]

                            exView.text = enome
                            serieView.text = eseries
                            repsView.text = ereps
                        } else {
                            // Hide the remaining views if there are fewer than 6 plans
                            val (exView, serieView, repsView) = planViews[i]
                            exView.text = ""
                            serieView.text = ""
                            repsView.text = ""
                        }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Failed to read plan data.", task.exception)
                    Toast.makeText(this, "Erro ao carregar planos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        setContentView(binding.root)

        binding.comecar.setOnClickListener {
            val intent = Intent(this@PlanoActivity, ExercicioActivity::class.java)
            startActivity(intent)
        }

        binding.icon1.setOnClickListener{
            val intent = Intent(this@PlanoActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@PlanoActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}