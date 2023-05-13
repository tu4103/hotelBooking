package com.tu.bookin_hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //button
        val buttonLogin: Button = findViewById(R.id.button_submit)
        val buttonRegister: Button = findViewById(R.id.button_register)

        //editText
        val editTextEmail: EditText = findViewById(R.id.email_login)
        val editTextPassword: EditText = findViewById(R.id.password_login)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (validate(email, password)) {
                if (login(email, password)) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String): Boolean {
        var check = false
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    check = true
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
}
        return check
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
}