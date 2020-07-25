package zero27zen.com


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.kantong.*
import zero27zen.com.holder.KantongAdapter
import zero27zen.com.model.isi


class kantong : AppCompatActivity() {
    private val TAG = "Trouble"
    private var mAdapter: KantongAdapter? = null
    private  var firestoreDB: FirebaseFirestore?=null
    private var firestoreListener: ListenerRegistration? = null
    val user = FirebaseAuth.getInstance().currentUser
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uname= user!!.uid
        firestoreDB = FirebaseFirestore.getInstance()
        val firestoreDBex=firestoreDB!!.collection("Barkas").document("transaksi").collection("isi")
            .document(uname).collection("JualBarang")
        setContentView(R.layout.kantong)
        loadNotesList()
        fno.setOnClickListener {
            val intent = Intent(this, Kosong::class.java)
            startActivity(intent)
        }
        firestoreListener = firestoreDBex
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }

                val notesList = mutableListOf<isi>()

                for (doc in documentSnapshots!!) {
                    val note = doc.toObject(isi::class.java)
                    note.uId= doc.id
                    notesList.add(note)
                }

                mAdapter = KantongAdapter(notesList, applicationContext)
                recyclekantong.adapter = mAdapter
            })
        setSupportActionBar(findViewById(R.id.mukiss))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        onDestroy()
    }
    private fun loadNotesList() {
        val eriko= user!!.uid
        firestoreDB!!
            .collection("Barkas")
            .document("transaksi").collection("isi")
            .document(eriko).collection("JualBarang")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val notesList = mutableListOf<isi>()
                    for (doc in task.result!!) {
                        val note = doc.toObject<isi>(isi::class.java)
                        note.uId= doc.id
                        notesList.add(note)
                    }

                    mAdapter = KantongAdapter(notesList, applicationContext)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recyclekantong.layoutManager = mLayoutManager
                    recyclekantong.itemAnimator = DefaultItemAnimator()
                    recyclekantong.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Data yang di ubah tidak tersimpan anda yakin akan kembali?")
            .setPositiveButton(android.R.string.yes) { dialog, whichButton ->
                startActivity(utama.getLaunchIntent(this))
                finish()

            }
            .setNegativeButton(android.R.string.no) { dialog, whichButton ->

            }
            .show()
    }  }

