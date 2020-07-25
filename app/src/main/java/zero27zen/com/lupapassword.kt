package zero27zen.com

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lupapassword.*

class lupapassword : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lupapassword)
        mAuth = FirebaseAuth.getInstance()
        //actionbar
        setSupportActionBar(findViewById(R.id.mantap))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
       reset_submit2.setOnClickListener {
           ProgressBar.VISIBLE// progressbar digunakan saat mengirim data ke email
           val email = emailganti2.text.toString().trim()
           if (TextUtils.isEmpty(email)) {
               ProgressBar.INVISIBLE
               Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
           } else {
               mAuth!!.sendPasswordResetEmail(email)
                   .addOnCompleteListener { task ->
                       if (task.isSuccessful) {
                           ProgressBar.INVISIBLE
                           emailganti2.setText(" ")
                           startActivity(Intent(this,MainActivity::class.java))
                           Toast.makeText(this, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
                       } else {
                           ProgressBar.INVISIBLE
                           Toast.makeText(this, "Fail to reset password Check your Email", Toast.LENGTH_SHORT).show()
                       }
                   }
           }
       }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        onDestroy()
    }
}