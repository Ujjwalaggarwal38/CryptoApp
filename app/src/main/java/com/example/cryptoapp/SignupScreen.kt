package com.example.cryptoapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cryptoapp.databinding.ActivitySignupScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SignupScreen : AppCompatActivity() {
    private val binding : ActivitySignupScreenBinding by lazy {
        ActivitySignupScreenBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Initialize firebase auth
        auth = FirebaseAuth.getInstance()


        setContentView(binding.root)
        binding.signup.setOnClickListener {
            startActivity(Intent(this,LoginScreen::class.java))
            finish()
        }
        binding.register.setOnClickListener {

            //get credentials from user

           val email = binding.email.text.toString()
           val username = binding.username.text.toString()
           val password = binding.password.text.toString()
           val confirmpassoword = binding.Confirmpass.text.toString()

            //check
            if(email.isEmpty() || username.isEmpty()|| password.isEmpty()|| confirmpassoword.isEmpty()){
                Toast.makeText(this,"Pls Fill all the details",Toast.LENGTH_LONG).show()
            }
            else if(password!=confirmpassoword){
                Toast.makeText(this,"Password and confirm password must be same",Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){
                        task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"User Created Successfully",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,LoginScreen::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Registration Failed ${task.exception?.message}",Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}