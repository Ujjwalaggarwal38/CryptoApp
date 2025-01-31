package com.example.cryptoapp

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {
    private val binding : ActivityWelcomeScreenBinding by lazy {
        ActivityWelcomeScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,LoginScreen::class.java))
        },3000)

        val welcometext="Welcome"
        val spannableText=SpannableString(welcometext)
        spannableText.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")),0,5,0)
        spannableText.setSpan(ForegroundColorSpan(Color.parseColor("#312222")),5,welcometext.length,0)
       binding.welcomeText.text=spannableText

    }
}