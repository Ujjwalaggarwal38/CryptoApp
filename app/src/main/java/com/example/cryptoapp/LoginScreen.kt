package com.example.cryptoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cryptoapp.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.AutoCloseable

class LoginScreen : AppCompatActivity() {
    private val binding:ActivityLoginScreenBinding by lazy {
        ActivityLoginScreenBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        //Check if user is currently logged in
        val currentUser:FirebaseUser?= auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth=FirebaseAuth.getInstance()


        setContentView(binding.root)
        binding.Signup.setOnClickListener {
            startActivity(Intent(this,SignupScreen::class.java))
            finish()
        }
        binding.Login.setOnClickListener {
            val email= binding.name.text.toString()
            val password= binding.password.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Pls Fill all the details", Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){
                        task->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Sign In successfull", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Sign In unsuccessfull ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }
}