package zero27zen.com

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lupapassword.*

class utama : AppCompatActivity() {
    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, utama::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    val mAuth=FirebaseAuth.getInstance()
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = home()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, transaksi::class.java)
                startActivity(intent)
            }
            R.id.navigation_jual -> {
                val fragment = jual()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.user-> {
                val fragment = biodata()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameutama, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama)
        val navView: BottomNavigationView = findViewById(R.id.bottombar)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val fragment = home()
        addFragment(fragment)
        val ekobar= findViewById<View>(R.id.hero) as Toolbar
        setSupportActionBar(ekobar)


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
val id = item?.itemId
        when (id) {
            R.id.keranjang -> {
                val intent = Intent(this, kantong::class.java)
                startActivity(intent)
            }
            R.id.out-> {
                signout()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun signout() {
        mAuth.signOut()
        startActivity(MainActivity.getLaunchIntent(this))
        finish()
    }


}