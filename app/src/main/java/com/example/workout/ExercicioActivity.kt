package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.Exercicio1Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class ExercicioActivity : ComponentActivity() {

    private lateinit var binding: Exercicio1Binding
    private lateinit var database: FirebaseDatabase
    private lateinit var uref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val planList = mutableListOf<Map<String, String>>()
    private var currentPlanIndex = 0
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Exercicio1Binding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        uref = database.getReference("Utilizadores")
        ref = database.getReference("Exercicios")

        setContentView(binding.root)

        if (user != null) {
            loadExercises(user.uid) {
                displayCurrentExercise()
            }
        }

        binding.avancar.setOnClickListener {
            if (currentPlanIndex < planList.size - 1) {
                currentPlanIndex++
                displayCurrentExercise()
            } else {
                // All exercises have been shown, change button function to navigate to another activity
                val intent = Intent(this@ExercicioActivity, PlanoFimActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadExercises(userId: String, onLoaded: () -> Unit) {
        uref.child(userId).child("plano").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snap = task.result
                for (planSnapshot in snap.children) {
                    val enome = planSnapshot.child("nome").value.toString()
                    val eseries = planSnapshot.child("series").value.toString()
                    val ereps = planSnapshot.child("reps").value.toString()
                    val plan = mapOf("nome" to enome, "series" to eseries, "reps" to ereps)
                    planList.add(plan)
                }
                onLoaded()
            } else {
                Log.w(ContentValues.TAG, "Failed to read plan data.", task.exception)
                Toast.makeText(this, "Erro ao carregar planos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayCurrentExercise() {
        if (currentPlanIndex < planList.size) {
            val plan = planList[currentPlanIndex]
            binding.exercicio.text = plan["nome"]
            binding.series.text = "${plan["series"]}x séries"
            binding.reps.text = "${plan["reps"]} repetições"
            ref.child(plan["nome"].toString()).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snap = task.result
                    val passo1 = snap.child("passo1").value.toString()
                    binding.passo1.text="1. $passo1"
                    val passo2 = snap.child("passo2").value.toString()
                    binding.passo2.text="2. $passo2"
                    val passo3 = snap.child("passo3").value.toString()
                    binding.passo3.text="3. $passo3"
                    val imgName = snap.child("img").value.toString()
                    when (imgName) {
                        "agachamento" -> binding.imagem.setImageResource(R.drawable.agachamento)
                        "stiff" -> binding.imagem.setImageResource(R.drawable.stiff)
                        "elevacao" -> binding.imagem.setImageResource(R.drawable.elevacao)
                        "stepup" -> binding.imagem.setImageResource(R.drawable.stepup)
                        "legpress" -> binding.imagem.setImageResource(R.drawable.legpress)
                        "extensora" -> binding.imagem.setImageResource(R.drawable.extensora)
                        // Add more cases as needed
                        else -> binding.imagem.setImageResource(R.drawable.pt3) // Fallback image
                    }

                } else {
                    Log.w(ContentValues.TAG, "Failed to read plan data.", task.exception)
                    Toast.makeText(this, "Erro ao carregar planos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}