package zero27zen.com


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.okhttp.internal.Util
import kotlinx.android.synthetic.main.activity_main.*
import zero27zen.com.model.User
import java.util.regex.Pattern
import java.util.regex.Pattern.compile


class new : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    var Fauth: FirebaseFirestore?=FirebaseFirestore.getInstance()
    var user=Fauth!!
        .collection("Barkas").document("user")
        .collection("userid")
    private val emailRegex = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.logoss))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val registerBtn = findViewById<View>(R.id.Submit) as Button
        registerBtn.setOnClickListener(View.OnClickListener {
                view -> registerUser()

        })
        //val password = findViewById<View>(R.id.savepass)as EditText
        //password.addTextChangedListener(object : TextWatcher {

            //override fun afterTextChanged(s: Editable) {
            //}

            ///override fun beforeTextChanged(s: CharSequence, start: Int,
                                //           count: Int, after: Int) {
            //}

            ////override fun onTextChanged(s: CharSequence, start: Int,
                                  //     before: Int, count: Int) {
               // var dwi = s.length
                //if (dwi==8){
                //    Toast.makeText(this@new,"Max Charakter",Toast.LENGTH_LONG).show()
              //  }else{
            //    }

          //  }
        //}) //


    }

    private fun registerUser() {
//Inisalisai Edittext ke register User
        val emailTxt = findViewById<View>(R.id.email) as EditText
        val passwordTxt = findViewById<View>(R.id.savepass) as EditText
        val nameTxt = findViewById<View>(R.id.isinama) as EditText
        val alamat = findViewById<View>(R.id.almt)as EditText
        val tellp =findViewById<View>(R.id.savetelp)as EditText
        val passwordsama = findViewById<View>(R.id.saveredu)as EditText
        //Implementasi ke variable local
        var alamate= alamat.text.toString()
        var hp = tellp.text.toString()
        var ulang = passwordsama.text.toString()
        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        var name = nameTxt.text.toString()
        if (password.length<=6){
            passwordTxt.error="Password Tidak boleh kurang 6 digit"
            Toast.makeText(this,"Password Kurang",Toast.LENGTH_LONG).show()
        }
       else if(name.isEmpty()) {
           Toast.makeText(this,"Nama Tidak Boleh Kosong",Toast.LENGTH_LONG).show()
       }
       else if (email.isEmpty()){
           Toast.makeText(this,"Email Tidak Boleh Kosong",Toast.LENGTH_LONG).show()}
       else if (hp.isEmpty()){ Toast.makeText(
           this,"Nomer Tidak Boleh Kosong",Toast.LENGTH_LONG).show()}
       else if (alamate.isEmpty()){ Toast.makeText(
           this,"Alamat Tidak Boleh Kosong",Toast.LENGTH_LONG).show()}
       else if (password.isEmpty()){
           Toast.makeText(this,"Password Tidak Boleh Kosong",Toast.LENGTH_LONG).show()}
       else if (ulang.isEmpty()){
           Toast.makeText(
               this,"Password Ulang Harus Di isi",Toast.LENGTH_LONG).show()}
       else{
           if (password!=ulang){
           Toast.makeText(this,"Password Tidak sama",Toast.LENGTH_LONG).show()
       }else if(PhoneNumberUtils.isGlobalPhoneNumber(hp)==false){
              Toast.makeText(this,"Nomer Tidak Valid",Toast.LENGTH_LONG).show()
          }
           else{
           mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
               if (task.isSuccessful) {
                   val gambaruser="https://firebasestorage.googleapis.com/v0/b/barkas-paktole.appspot.com/o/user%2FnoImage.png?alt=media&token=18d084b9-1d29-42a0-9453-fe940fe0924f"
                   val user = mAuth.currentUser
                   var uId= user!!.uid
                   getandset(uId!!,name,hp,alamate,gambaruser)
                   startActivity(Intent(this, MainActivity::class.java))
                   Toast.makeText(this, "Successfully registered :)", Toast.LENGTH_LONG).show()
               }else {
                   isEmailValid(email)

               }
           })}
       }
    }

    private fun getandset(uId: String,
                          name: String,hp:String, alamat:String,gambaruser:String){
        val data = User(name,hp,alamat,gambaruser).toMap()
        user.document(uId).set(data)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        onDestroy()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (networkInfo==null){
                Toast.makeText(this,"Periksa Jaringan Anda",Toast.LENGTH_LONG).show()
            }else{ Toast.makeText(this,"Email Sudah Terpakai ",Toast.LENGTH_LONG).show()
                networkInfo?.isConnected == true}
            networkInfo?.isConnected ?: false
        } else{ false}
    }
    fun isEmailValid(email: String) {
       val eko = email
     if(emailRegex.matcher(eko).matches()){
         isNetworkAvailable()

     }else
     {Toast.makeText(this,"email "+eko+" tidak valid",Toast.LENGTH_LONG).show()


     }
    }




}