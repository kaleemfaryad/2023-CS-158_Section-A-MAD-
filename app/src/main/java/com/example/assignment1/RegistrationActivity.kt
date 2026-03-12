package com.example.assignment1

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var etFullName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var spinnerEventType: Spinner
    private lateinit var tvSelectedDate: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var ivProfile: ImageView
    private lateinit var cbTerms: CheckBox
    private var selectedDate: String = ""
    private var selectedImageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            ivProfile.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        spinnerEventType = findViewById(R.id.spinnerEventType)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        rgGender = findViewById(R.id.rgGender)
        ivProfile = findViewById(R.id.ivProfile)
        cbTerms = findViewById(R.id.cbTerms)

        val btnPickDate = findViewById<Button>(R.id.btnPickDate)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Spinner Setup
        val eventTypes = arrayOf("Seminar", "Workshop", "Conference", "Webinar", "Cultural Event")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEventType.adapter = adapter

        // Date Picker
        btnPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, yearSelected, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth/${monthOfYear + 1}/$yearSelected"
                tvSelectedDate.text = selectedDate
            }, year, month, day)
            datePickerDialog.show()
        }

        // Image Picker
        btnUploadImage.setOnClickListener {
            getContent.launch("image/*")
        }

        // Submit Button
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                showConfirmationDialog()
            }
        }
    }

    private fun validateForm(): Boolean {
        val name = etFullName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val genderId = rgGender.checkedRadioButtonId

        if (name.isEmpty()) {
            etFullName.error = "Name is required"
            return false
        }
        if (phone.isEmpty()) {
            etPhone.error = "Phone is required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email"
            return false
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select an event date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (genderId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Registration")
            .setMessage("Are you sure you want to submit the registration?")
            .setPositiveButton("Yes") { _, _ ->
                navigateToConfirmation()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun navigateToConfirmation() {
        val selectedGenderId = rgGender.checkedRadioButtonId
        val selectedGender = findViewById<RadioButton>(selectedGenderId).text.toString()
        val eventType = spinnerEventType.selectedItem.toString()

        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra("NAME", etFullName.text.toString())
            putExtra("PHONE", etPhone.text.toString())
            putExtra("EMAIL", etEmail.text.toString())
            putExtra("EVENT_TYPE", eventType)
            putExtra("EVENT_DATE", selectedDate)
            putExtra("GENDER", selectedGender)
            putExtra("IMAGE_URI", selectedImageUri.toString())
        }
        startActivity(intent)
    }
}