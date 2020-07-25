package zero27zen.com

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.editdata.*
import kotlinx.android.synthetic.main.jual_barang.*
import zero27zen.com.model.Post
import java.sql.Timestamp
import java.util.*

class Edit : AppCompatActivity() {
    var TAG="tag"
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var judul:String?=null
    var isijudul:Int?=null
    var jenis:String?=null
    var date= Timestamp(System.currentTimeMillis())
    var dwi=""
    var id=" "
    var kategori=" "
    var jenisi=""
    var dbfirestore: FirebaseFirestore? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editdata)
        setSupportActionBar(findViewById(R.id.editbar))
        firebaseStore= FirebaseStorage.getInstance()
        storageReference= firebaseStore!!.reference
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(intent.extras != null){
            val bundle = intent.extras
            id= bundle.getString("uid")
            barangnamas.setText(bundle.getString("nama"))
            kategori=bundle.getString("kategori")
            jenisi =bundle.getString("jenis")
            beratbarangs.setText(bundle.getString("berat"))
            var gambarku=bundle.getString("gb")
            dwi=gambarku
            deskripsibarangs.setText(bundle.getString("keterangan"))
            Picasso.get().load(gambarku)
                .into(imageView7s)
           }
        else{}
        imageView7s.setOnClickListener{
            val inten = Intent(Intent.ACTION_PICK)
            inten.type="image/*"
            startActivityForResult(inten,0)
        }

        val spinner = spinner2s
        val myStrings = arrayOf("Kategori Besi", "Kategori Plastik", "Kategori Elektronik", "Kategori Kertas")
        spinner.adapter = ArrayAdapter( this,
            android.R.layout.simple_expandable_list_item_1, myStrings)
        spinner.setSelection(myStrings.indexOf(kategori))
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val progress=ProgressDialog(this@Edit)
                progress.setMessage("Memuat Data")
                progress.setCancelable(false)
                progress.show()
                if (myStrings[p2]=="Kategori Besi"){
                    kategoriss.text="Besi"
                    judul= myStrings[p2]
                    dbfirestore= FirebaseFirestore.getInstance()
                    dbfirestore!!.collectionGroup("besi")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val spin = spinnersss
                                val mystring2= ArrayList<String>()
                                val mystring3= ArrayList<String>()
                                for (doc in task.result!!) {
                                    val note = doc.toObject<Post>(Post::class.java)
                                    note.id=doc.id
                                    note.Nama=doc.getString("Nama")
                                    mystring2.add(note.Nama!!)
                                    mystring3.add(note.harga.toString()!!)
                                    spin.adapter = ArrayAdapter( this@Edit,
                                        android.R.layout.simple_expandable_list_item_1, mystring2)
                                    spin.setSelection(mystring2.indexOf(jenisi))
                                    spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                        {  peetapanharga.text="Rp "+mystring3[p2]+" Perkilo"
                                            jenisz.text=mystring2[p2]
                                            jenis=jenisz.text.toString()
                                            isijudul=mystring3[p2].toInt()
                                            progress.dismiss()

                                        }

                                        override fun onNothingSelected(p0: AdapterView<*>?) {
                                        }
                                    }
                                }

                            } else {
                                Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                            }
                        }

                }else{
                    if (myStrings[p2]=="Kategori Plastik"){
                        judul=myStrings[p2]
                        dbfirestore= FirebaseFirestore.getInstance()
                        dbfirestore!!.collectionGroup("plastik")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val spin = spinnersss
                                    val mystring2= ArrayList<String>()
                                    val mystring3= ArrayList<String>()


                                    for (doc in task.result!!) {
                                        val note = doc.toObject<Post>(Post::class.java)
                                        note.id=doc.id
                                        note.Nama=doc.getString("Nama")
                                        mystring2.add(note.Nama!!)
                                        mystring3.add(note.harga.toString()!!)
                                        spin.adapter = ArrayAdapter( this@Edit,
                                            android.R.layout.simple_expandable_list_item_1, mystring2)
                                        spin.setSelection(mystring2.indexOf(jenisi))
                                        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                            { kategoriss.text="Plastik"
                                                peetapanharga.text="Rp "+mystring3[p2]+" Perkilo"
                                                jenisz.text=mystring2[p2]
                                                jenis=jenisz.text.toString()
                                                isijudul=mystring3[p2].toInt()
                                                progress.dismiss()


                                            }

                                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                            }
                                        }
                                    }

                                } else {
                                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                                }
                            }

                    }
                    else{if (myStrings[p2]=="Kategori Elektronik"){
                        kategoriss.text="Elektronik"
                        judul=myStrings[p2]
                        dbfirestore= FirebaseFirestore.getInstance()
                        dbfirestore!!.collectionGroup("elektronik")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val spin = spinnersss
                                    val mystring2= ArrayList<String>()
                                    val mystring3= ArrayList<String>()


                                    for (doc in task.result!!) {
                                        val note = doc.toObject<Post>(Post::class.java)
                                        note.id=doc.id
                                        note.Nama=doc.getString("Nama")
                                        mystring2.add(note.Nama!!)
                                        mystring3.add(note.harga.toString()!!)
                                        spin.adapter = ArrayAdapter(this@Edit,
                                            android.R.layout.simple_expandable_list_item_1, mystring2)
                                        spin.setSelection(mystring2.indexOf(jenisi))
                                        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                            { peetapanharga.text="Rp "+mystring3[p2]+" PerUnit"
                                                peetapanharga.text="Rp "+mystring3[p2]+" Perkilo"
                                                jenisz.text=mystring2[p2]
                                                jenis=jenisz.text.toString()
                                                isijudul=mystring3[p2].toInt()
                                                progress.dismiss()
                                            }

                                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                            }
                                        }
                                    }

                                } else {
                                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                                }
                            }

                    }
                    else{
                        judul=myStrings[p2]
                        dbfirestore= FirebaseFirestore.getInstance()
                        dbfirestore!!.collectionGroup("kertas")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val spin = spinnersss
                                    val mystring2= ArrayList<String>()
                                    val mystring3= ArrayList<String>()
                                    val mystring4= ArrayList<Int>()


                                    for (doc in task.result!!) {
                                        val note = doc.toObject<Post>(Post::class.java)
                                        note.id=doc.id
                                        note.Nama=doc.getString("Nama")
                                        mystring2.add(note.Nama!!)
                                        mystring3.add(note.harga.toString()!!)
                                        spin.adapter = ArrayAdapter(this@Edit,
                                            android.R.layout.simple_expandable_list_item_1, mystring2)
                                        spin.setSelection(mystring2.indexOf(jenisi))
                                        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                            { peetapanharga.text="Rp "+mystring3[p2]+" Perkilo"
                                                jenisz.text=mystring2[p2]
                                                jenis=jenisz.text.toString()
                                                isijudul=mystring3[p2].toInt()
                                                progress.dismiss()

                                            }

                                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                            }
                                        }
                                    }

                                } else {
                                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                                }
                            }}}
                }
                Handler().postDelayed({progress.dismiss()},10000)
            }

        }
        Editsave.setOnClickListener {
            cek()
        }

    }

    private fun cek() {
        if (barangnamas.text.isEmpty()){
            barangnamas.error="Nama barang Tidak Boleh Kosong"
            Toast.makeText(this,"Error Masukan", Toast.LENGTH_LONG).show()
        } else{
            if (beratbarangs.text.isEmpty()){
                beratbarangs.error="Berat Tidak Boleh kosong"
                Toast.makeText(this,"Error Masukan", Toast.LENGTH_LONG).show()
            }else{
                if (deskripsibarangs.text.isEmpty()){
                    deskripsibarangs.error="Detail Barang harus di isi"
                    Toast.makeText(this,"Error Masukan", Toast.LENGTH_LONG).show()
                }else{
                    uploadimage()
                }
            }
        }
    }


    private fun updatedata(ejo: String) {
        updates(ejo,judul,isijudul,date,barangnamas.text.toString(),beratbarangs.text,dwi)
    }

    private fun updates(
        ejo: String,
        judul: String?,
        isijudul: Int?,
        date: Timestamp,
        tosa: String,
        tosaki:Editable,
        dwi: String
    ) {


        var db =FirebaseFirestore.getInstance()
       var kyu= db.collection("Barkas").document("transaksi").collection("isi")
            .document(ejo).collection("JualBarang").document(id)
        var kyuki:Number
        kyuki=tosaki.toString().toFloat()
        var harga:Number
        harga= isijudul!!.times(kyuki)
        kyu.update("namabarang",tosa)
        kyu.update("kategori",judul)
        kyu.update("berat",kyuki)
        kyu.update("hargabarng",harga)
        kyu.update("keterangan",deskripsibarangs.text.toString())
        kyu.update("jenis",jenisz.text.toString())
        kyu.update("date",date)
        kyu.update("gbarang",dwi)
        val intent = Intent(this, kantong::class.java)
        startActivity(intent)

    }

    private fun uploadimage()  {
        val user = FirebaseAuth.getInstance().currentUser
        val progress= ProgressDialog(this)
        progress.setMessage("Menyimpan Data...")
        progress.setCancelable(false)
        progress.show()

        if (filePath != null) {
            val filename = UUID.randomUUID().toString()
            val ref = storageReference?.child("transaksi/$filename")

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

            imageView7s.setImageBitmap(bitmap)

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



