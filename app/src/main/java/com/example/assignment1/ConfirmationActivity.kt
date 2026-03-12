package com.example.assignment1

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val name = intent.getStringExtra("NAME")
        val phone = intent.getStringExtra("PHONE")
        val email = intent.getStringExtra("EMAIL")
        val eventType = intent.getStringExtra("EVENT_TYPE")
        val eventDate = intent.getStringExtra("EVENT_DATE")
        val gender = intent.getStringExtra("GENDER")
        val imageUriString = intent.getStringExtra("IMAGE_URI")

        findViewById<TextView>(R.id.tvConfirmName).text = "Full Name: $name"
        findViewById<TextView>(R.id.tvConfirmPhone).text = "Phone Number: $phone"
        findViewById<TextView>(R.id.tvConfirmEmail).text = "Email Address: $email"
        findViewById<TextView>(R.id.tvConfirmEventType).text = "Event Type: $eventType"
        findViewById<TextView>(R.id.tvConfirmDate).text = "Event Date: $eventDate"
        findViewById<TextView>(R.id.tvConfirmGender).text = "Gender: $gender"

        if (imageUriString != null) {
            findViewById<ImageView>(R.id.ivConfirmedProfile).setImageURI(Uri.parse(imageUriString))
        }

        findViewById<Button>(R.id.btnBackHome).setOnClickListener {
            finish()
        }
    }
}