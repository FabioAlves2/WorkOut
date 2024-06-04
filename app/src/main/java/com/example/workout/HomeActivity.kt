package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.marginEnd
import com.example.workout.databinding.HomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class HomeActivity : ComponentActivity() {

    private lateinit var binding: HomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var uref: DatabaseReference
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        database = Firebase.database
        uref = database.getReference("Utilizadores")
        ref = database.getReference("Personal")

        if (user != null) {
            uref.child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val snap = task.result
                    val unome = snap.child("nome").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $unome")
                    binding.olamsg.text="Olá $unome!"
                    val pt = snap.child("Pt").value.toString()
                    Log.d(ContentValues.TAG, "Value is: $pt")
                    loadPlano(user.uid)

                    //ler qual o pt associado
                    ref.child(pt).get().addOnCompleteListener { task1 ->
                        if (task1.isSuccessful){
                            val snap1 = task1.result
                            val nome = snap1.child("nome").value.toString()
                            val img = snap1.child("img").value
                            Log.d(ContentValues.TAG, "Value is: $nome")
                            if (nome!="null"){
                                binding.topTextView.text=nome
                                binding.bottomTextView.text="Está disponível para esclarecer dúvidas!"
                                if (img == "pt1"){
                                    binding.centeredImageView.setImageResource(R.drawable.pt1)
                                }else if (img == "pt2"){
                                    binding.centeredImageView.setImageResource(R.drawable.pt2)
                                }else if (img == "pt3"){
                                    binding.centeredImageView.setImageResource(R.drawable.pt3)
                                }else{
                                    binding.centeredImageView.setImageResource(R.drawable.pt4)
                                }

                            }else{
                                binding.topTextView.text=""
                                binding.bottomTextView.text = "Selecione um dos nossos Personal Tariners para ter um acompanhamento personalizado."
                            }
                        }else{
                            Log.w(ContentValues.TAG, "Failed to read value.")
                            Toast.makeText(this@HomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
                        }
                    }


                }else{
                    Log.w(ContentValues.TAG, "Failed to read value.")
                    Toast.makeText(this@HomeActivity, "Erro!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)

        binding.plano.setOnClickListener {
            val intent = Intent(this@HomeActivity, PlanoActivity::class.java)
            startActivity(intent)
        }

        binding.icon3.setOnClickListener {
            val intent = Intent(this@HomeActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        binding.icon4.setOnClickListener {
            val intent = Intent(this@HomeActivity, StatsActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@HomeActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadPlano(userId: String) {
        uref.child(userId).child("plano").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snap = task.result
                val planViews = arrayOf(
                    Triple(binding.ex1, binding.serie1, binding.reps1),
                    Triple(binding.ex2, binding.serie2, binding.reps2),
                    Triple(binding.ex3, binding.serie3, binding.reps3),
                    Triple(binding.ex4, binding.serie4, binding.reps4),
                    Triple(binding.ex5, binding.serie5, binding.reps5),
                    Triple(binding.ex6, binding.serie6, binding.reps6)
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
}