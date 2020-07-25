package zero27zen.com

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.harga.*
import zero27zen.com.holder.elektro
import zero27zen.com.model.Post

class elektronik : AppCompatActivity() {
    private val TAG = "Trouble"
    private var mAdapter: elektro? = null
    private  var firestoreDB: FirebaseFirestore?=null
    private var firestoreListener: ListenerRegistration? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestoreDB = FirebaseFirestore.getInstance()
        val firestoreDBex=firestoreDB!!.collection("Barkas").document("jenis").collection("elektronik")
        setContentView(R.layout.harga)
        loadNotesList()
        firestoreListener = firestoreDBex
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }

                val notesList = mutableListOf<Post>()

                for (doc in documentSnapshots!!) {
                    val note = doc.toObject(Post::class.java)
                    note.id = doc.id
                    notesList.add(note)
                }

                mAdapter = elektro(notesList, applicationContext, firestoreDB!!)
                kemunculan.adapter = mAdapter
            })

        setSupportActionBar(findViewById(R.id.ekolis))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val nama= findViewById<View>(R.id.semicolon) as TextView
        nama.text = "Daftar Harga Elektronik Bekas"
    }

    private fun loadNotesList() {
        firestoreDB!!
            .collection("Barkas").document("jenis").collection("elektronik")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val notesList = mutableListOf<Post>()

                    for (doc in task.result!!) {
                        val note = doc.toObject<Post>(Post::class.java)
                        note.id = doc.id
                        notesList.add(note)
                    }

                    mAdapter = elektro(notesList, applicationContext, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    kemunculan.layoutManager = mLayoutManager
                    kemunculan.itemAnimator = DefaultItemAnimator()
                    kemunculan.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        onDestroy()
    }
}