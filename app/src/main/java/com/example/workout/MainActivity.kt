package com.example.workout

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workout.databinding.HomeBinding
import com.example.workout.databinding.LoginBinding
import com.example.workout.ui.theme.WorkOutTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var pref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "Utilizador já está logado. ID do utilizador: ${currentUser.uid}")
            verificaruser(currentUser.uid)
        } else {
            Log.d(TAG, "Nenhum utilizador logado no momento.")
        }
        setContentView(binding.root)

        // Configuração do botão de login
        binding.butLogin.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Log.d(TAG, "Tentativa de login com o email: $email")
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Login bem-sucedido!")
                            val user = auth.currentUser
                            if (user != null) {
                                verificaruser(user.uid)
                            }
                        } else {
                            Log.w(TAG, "Falha ao fazer login.", task.exception)
                            Toast.makeText(this@MainActivity, "Erro! Verifique suas credenciais!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Log.d(TAG, "Campos de email e/ou senha não preenchidos.")
                Toast.makeText(this@MainActivity, "Preencha os campos de email e senha!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verificaruser(userId: String) {
        Log.d(TAG, "Verificando informações do utilizador na base de dados. ID do usuário: $userId")
        ref = Firebase.database.getReference("Utilizadores")
        pref = Firebase.database.getReference("Personal")

        //check if the userId is in the pref as a child
        //if it is call the PtHomeActivity
        //if is not a Personal, do this:

        pref.child(userId).get().addOnCompleteListener { snapshot ->
            if (snapshot.isSuccessful && snapshot.result.exists()) {
                Log.d(TAG,"Perfil de Personal encontrado na base de dados. Ir para a HomeActivity.")
                val intent = Intent(this@MainActivity, PtHomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d(TAG, "Perfil de Personal não encontrado na base de dados. Verificar se é utilizador?!")
                ref.child(userId).get().addOnCompleteListener { snapshot2 ->
                    if (snapshot2.isSuccessful && snapshot2.result.exists()) {
                        Log.d(TAG, "Perfil de utilizador encontrado na base de dados. Ir para a HomeActivity.")
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d(TAG, "Perfil de utilizador não encontrado na base de dados. Ir para a NovoPerfilActivity.")
                        val intent = Intent(this@MainActivity, NovoPerfilActivity::class.java)
                        intent.putExtra("email", binding.editTextTextEmailAddress.text.toString().trim())
                        startActivity(intent)
                    }
                }
            }
        }
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}