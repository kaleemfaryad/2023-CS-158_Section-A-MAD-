package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.materialswitch.MaterialSwitch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRegister = findViewById<Button>(R.id.btnRegisterEvent)
        val themeSwitch = findViewById<MaterialSwitch>(R.id.themeSwitch)

        // Initialize switch state based on current theme
        themeSwitch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        btnRegister.setOnClickListener {
            // Simple scale animation on click
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).withEndAction {
                    val intent = Intent(this, RegistrationActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }
        
        // Add a subtle entrance animation to the card
        findViewById<View>(R.id.cardHero).apply {
            alpha = 0f
            translationY = 50f
            animate().alpha(1f).translationY(0f).setDuration(800).setStartDelay(300).start()
        }
    }
}