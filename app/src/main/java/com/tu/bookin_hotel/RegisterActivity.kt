package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //button
        val buttonSubmit: Button = findViewById(R.id.button_submit)
        val buttonLogin: Button = findViewById(R.id.button_login)
        // edit text
        val editTextEmail: EditText = findViewById(R.id.email_register)
        val editTextPassword: EditText = findViewById(R.id.password_register)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        buttonSubmit.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (validate(email, password)) {
                checkEmailExits(email) { emailExists ->
                    if (emailExists) {
                        Toast.makeText(this, "Account exists", Toast.LENGTH_SHORT).show()
                    } else {
                        registerUser(email, password) { success ->
                            if (success) {
                                Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                                val timer = Timer()
                                timer.schedule(object : TimerTask() {
                                    override fun run() {
                                        val intent =
                                            Intent(this@RegisterActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                }, 3000)
                            } else {
                                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkEmailExits(email: String, callback: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
            .addOnSuccessListener { result ->
                if (result.signInMethods?.isNotEmpty() == true) {
                    callback(true)
                } else {
                    callback(false)
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Happened an error: ${exception.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun validate(email: String, password: String): Boolean {
        val regexEmail =
            Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
        if (!regexEmail.matches(email)) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        return true
    }

    private fun registerUser(email: String, password: String, callback: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Happened an error: ${exception.message}", Toast.LENGTH_SHORT)
                    .show()
                callback(false)
            }
    }
}