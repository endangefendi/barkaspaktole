package zero27zen.com

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.biodata.*
import kotlinx.android.synthetic.main.biodata.view.*
import zero27zen.com.model.User

class biodata : Fragment() {
    var Fauth: FirebaseFirestore?= FirebaseFirestore.getInstance()
    var user=Fauth!!
        .collection("Barkas").document("user")
        .collection("userid")
     var image :String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.biodata, container, false)
        view.editbio.setOnClickListener{
            val nama=eriko
            var alamat=erikoaddres
            var hp=erikophone

            val bundle = Bundle()
            bundle.putString("nama",nama.text.toString())
            bundle.putString("alamat",alamat.text.toString())
            bundle.putString("hp",hp.text.toString())
            bundle.putString("gambar",image)
            val intent = Intent(context, Isibiodara::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        return view
    }


        override fun onStart() {
            super.onStart()
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val ejo=user.uid
                getdata(ejo)
            }
        }

    private fun getdata(ejo: String) {
         val nama=eriko
         var alamat=erikoaddres
        var hp=erikophone

        var gambar = imageView5
       user.document(ejo).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val notesList = mutableListOf<User>()
                    val note = document.toObject<User>(User::class.java)
                    note!!.uId=document.id
                    note.alamat=document.getString("Alamat")
                    note.gambaruser=document.getString("Gambar")
                    note.name=document.getString("Nama")
                    note.hp=document.getString("NoHp")
                    notesList.add(note)
                    nama.text=note.name
                    alamat.text=note.alamat
                    hp.text=note.hp
                    image= note.gambaruser.toString()
                    Picasso.get().load(image).into(gambar)
                    progressBar.visibility=View.GONE
                    } else {

                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

}

