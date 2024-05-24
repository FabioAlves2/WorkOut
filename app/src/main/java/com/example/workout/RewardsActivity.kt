package com.example.workout

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
                if(img=="rew1"){
                    rimg.setImageResource(R.drawable.pt1)
                } else if(img =="rew2"){
                    rimg.setImageResource(R.drawable.pt2)
                } else if(img =="rew3"){
                    rimg.setImageResource(R.drawable.pt3)
                } else{
                    rimg.setImageResource(R.drawable.plano1)
                }

            } else {
                Log.w(ContentValues.TAG, "Failed to read value.")
                Toast.makeText(this@RewardsActivity, "Erro!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}