package zero27zen.com

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.homepage.*


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences : SharedPreferences


    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)
        setupUI()
        firebaseAuth = FirebaseAuth.getInstance()
       sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        isiemail.setText(sharedPreferences.getString("email", ""))
        isipassword.setText(sharedPreferences.getString("password", ""))
        chkSave.isChecked = sharedPreferences.getBoolean("check", false)
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(utama.getLaunchIntent(this))
            finish()
        }
    }

    private fun setupUI() {
        loginss.setOnClickListener{
            login()
        }
        daftar.setOnClickListener{
            register()
        }

        lupapas.setOnClickListener{
            startActivity(Intent(this, lupapassword::class.java))
        }

    }



    private fun login () {
        val emailTxt = findViewById<View>(R.id.isiemail) as EditText //variable untuk membaca field isiemail dalam MainActivity.kt
        var email = emailTxt.text.toString() // variable yang digunakan untuk merubah tipe data edittext ke string
        val passwordTxt = findViewById<View>(R.id.isipassword) as EditText //variable untuk membaca field isipassword dalam MainActivity.kt
        var password = passwordTxt.text.toString()// variable yang digunakan untuk merubah tipe data edittext ke string

        if (email.isEmpty() && (password.isEmpty())){
            emailTxt.error="Email Tidak Boleh Kosong"
            passwordTxt.error="Password Tidak Boleh Kosong"
            Toast.makeText(this,"Error Login",Toast.LENGTH_LONG).show()
        }
        else if (email.isEmpty()) {
            emailTxt.error="Email Tidak Boleh Kosong"
        }
        else if (password.isEmpty()){
            passwordTxt.error="Password Tidak Boleh Kosong"
        }
        else if (!email.isEmpty()&& (!password.isEmpty())){
            this.firebaseAuth?.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged in :)", Toast.LENGTH_LONG).show()
                       if (chkSave.isChecked) {
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("email", email)
                            editor.putString("password", password)
                            editor.putBoolean("check", true)
                            editor.commit()
                        } else {
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.remove("email")
                            editor.remove("password")
                            editor.remove("check")
                            editor.commit()
                        }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error Login Pastikan Email Dan Password Benar", Toast.LENGTH_SHORT).show()
                    }
                })

        }
    }


    private fun register () {
        startActivity(Intent(this, new::class.java))
    }

}



