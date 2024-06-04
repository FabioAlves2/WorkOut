package com.example.workout

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.workout.databinding.RewardsBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class RewardsActivity : ComponentActivity() {

    private lateinit var binding: RewardsBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RewardsBinding.inflate(layoutInflater)
        database = Firebase.database
        ref = database.getReference("Rewards")

        getreward("reward1",binding.nome1,binding.desc1,binding.img1)
        getreward("reward2",binding.nome2,binding.desc2,binding.img2)
        getreward("reward3",binding.nome3,binding.desc3,binding.img3)
        getreward("reward4",binding.nome4,binding.desc4,binding.img4)
        getreward("reward5",binding.nome5,binding.desc5,binding.img5)
        setContentView(binding.root)



        binding.back.setOnClickListener {
            val intent = Intent(this@RewardsActivity, StatsActivity::class.java)
            startActivity(intent)
        }

        binding.icon1.setOnClickListener{
            val intent = Intent(this@RewardsActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.icon4.setOnClickListener {
            val intent = Intent(this@RewardsActivity, StatsActivity::class.java)
            startActivity(intent)
        }

        binding.icon5.setOnClickListener {
            val intent = Intent(this@RewardsActivity, PerfilActivity::class.java)
            startActivity(intent)
        }

        //recompensas
        binding.rew1.setOnClickListener {
            showDialog("Recompensa 1", "Quer mesmo recolher uma mensalidade grátis?")
        }

        binding.rew2.setOnClickListener {
            showDialog("Recompensa 2", "Quer mesmo recolher uma embalagem de creatina?")
        }

        binding.rew3.setOnClickListener {
            showDialog("Recompensa 3", "Quer mesmo recolher um cupão de desconto?")
        }

        binding.rew4.setOnClickListener {
            showDialog("Recompensa 4", "Quer mesmo recolher um caixa de barrilhas?")
        }

        binding.rew5.setOnClickListener {
            showDialog("Recompensa 5", "Quer mesmo recolher uma garrafa de desporto?")
        }
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Sim") { dialog, which ->
            val intent = Intent(this@RewardsActivity, WithdrawnActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun getreward(rew: String, rnome: TextView, rdescri: TextView, rimg: ImageView) {
        ref.child(rew).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snap = task.result
                val nome = snap.child("nome").value.toString()
                Log.d(ContentValues.TAG, "Value is: $nome")
                val descri = snap.child("descricao").value.toString()
                val pontos = snap.child("pontos").value.toString()
                val img = snap.child("img").value.toString()
                rnome.text=nome
                rdescri.text="Por $pontos pontos recebe $descri."
                when (img) {
                    "mensalidade" -> rimg.setImageResource(R.drawable.mensalidade)
                    "desconto" -> rimg.setImageResource(R.drawable.desconto)
                    "creatina" -> rimg.setImageResource(R.drawable.creatina)
                    "barrinhas" -> rimg.setImageResource(R.drawable.barrinhas)
                    "garrafa" -> rimg.setImageResource(R.drawable.garrafa)
                    else -> rimg.setImageResource(R.drawable.garrafa)
                }

            } else {
                Log.w(ContentValues.TAG, "Failed to read value.")
                Toast.makeText(this@RewardsActivity, "Erro!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}