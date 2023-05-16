package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin: Button = findViewById(R.id.button_submit)
        val buttonRegister: Button = findViewById(R.id.button_register)
        val editTextEmail: EditText = findViewById(R.id.email_login)
        val editTextPassword: EditText = findViewById(R.id.password_login)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (validate(email, password)) {
                login(email, password)
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG, "signInWithEmailAndPassword:success")
                val user = FirebaseAuth.getInstance().currentUser
                updateUI(user)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "signInWithEmailAndPassword:failure", exception)
                Toast.makeText(this, "Login Failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun validate(email: String, password: String): Boolean {
        val regexEmail = Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
        if (!regexEmail.matches(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is logged in, navigate to home activity or perform desired actions
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not logged in, stay on login activity or perform desired actions
        }
    }
}