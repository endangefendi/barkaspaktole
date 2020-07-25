package zero27zen.com

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.relog.*
import zero27zen.com.holder.Logsukses
import zero27zen.com.model.log

class Riwayat : Fragment() {
    private val TAG = "Trouble"
    val user = FirebaseAuth.getInstance().currentUser
    private var mAdapter: Logsukses? = null
    private  var firestoreDB: FirebaseFirestore?=null
    private var firestoreListener: ListenerRegistration? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.relog, container, false)
        firestoreDB = FirebaseFirestore.getInstance()
        loadNotesList()
        return view

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
                    val notesList = mutableListOf<log>()
                    for (doc in task.result!!) {
                        val note = doc.toObject<log>(log::class.java)
                        note.uId= doc.id
                        notesList.add(note)
                    }

                    mAdapter = Logsukses(notesList, view!!.context)
                    val mLayoutManager = LinearLayoutManager(view?.context)
                    hidupsatuhati.layoutManager = mLayoutManager
                    hidupsatuhati.itemAnimator = DefaultItemAnimator()
                    hidupsatuhati.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

}