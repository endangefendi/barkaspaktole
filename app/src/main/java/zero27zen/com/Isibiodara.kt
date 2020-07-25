package zero27zen.com


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.isi_biodata.*
import zero27zen.com.model.User
import java.util.*


class Isibiodara : AppCompatActivity() {
    var TAG="tag"
    var Fauth: FirebaseFirestore?= FirebaseFirestore.getInstance()
    private var filePath: Uri? = null
    var user=Fauth!!
        .collection("Barkas").document("user")
        .collection("userid")
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var dwi=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isi_biodata)
        setSupportActionBar(findViewById(R.id.mukill))
        firebaseStore= FirebaseStorage.getInstance()
        storageReference= firebaseStore!!.reference
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(intent.extras != null){
            val bundle = intent.extras
            bionama.setText(bundle.getString("nama"))
            bioalamat.setText(bundle.getString("alamat"))
            biohp.setText(bundle.getString("hp"))
            dwi=bundle.getString("gambar")
            Picasso.get().load(dwi).into(imageView6)}
        else{}
        button3.setOnClickListener{
            val inten =Intent(Intent.ACTION_PICK)
            inten.type="image/*"
            startActivityForResult(inten,0)

        }
        simpancoyy.setOnClickListener {
            uploadimage()

        }

    }



    private fun updatedata(ejo: String) {
        var nama=bionama.text.toString()
        var alamat=bioalamat.text.toString()
        var hp=biohp.text.toString()
        var gambar= dwi
        var uId= ejo

        updates(uId!!,nama,hp,alamat,gambar)
    }

    private fun updates(uId: String, nama: String, hp: String, alamat: String, gambar: String) {
        val data = User(nama,hp,alamat,gambar).toMap()
        user.document(uId).update(data)
        startActivity(utama.getLaunchIntent(this))
    }

    private fun uploadimage()  {
        val user = FirebaseAuth.getInstance().currentUser
        val progress=ProgressDialog(this)
        progress.setMessage("Menyimpan Data...")
        progress.setCancelable(false)
        progress.show()

        if (filePath != null) {
            val filename = UUID.randomUUID().toString()
            val ref = storageReference?.child("user/$filename")

        ref!!.putFile(filePath!!)
            .addOnSuccessListener {
                              ref.downloadUrl.addOnSuccessListener {
                    dwi=it.toString()
                    if (user != null) {
                        val ejo=user.uid
                        updatedata(ejo)
                        Handler().postDelayed({progress.dismiss()},500)
                    }
                          }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
        }
        else{
            updatedata( user!!.uid)
        }

    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        onDestroy()
        finish()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d("", "Photo was selected")

            filePath= data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

            imageView6.setImageBitmap(bitmap)

        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Data yang di ubah tidak tersimpan anda yakin akan kembali?")
            .setPositiveButton(android.R.string.yes) { dialog, whichButton ->
                super.onBackPressed()

            }
            .setNegativeButton(android.R.string.no) { dialog, whichButton ->

            }
            .show()
    }  }



