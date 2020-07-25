package zero27zen.com

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.homepage.*
import kotlinx.android.synthetic.main.jual_barang.*
import zero27zen.com.model.Post
import zero27zen.com.model.User
import zero27zen.com.model.transaksi
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class Kosong: AppCompatActivity() {
    var kategori:String?=null
    val user = FirebaseAuth.getInstance().currentUser
    var Fauth: FirebaseFirestore?= FirebaseFirestore.getInstance()
    var transaksijual =Fauth!!
        .collection("Barkas").document("transaksi").collection("isi")
    var judul:String?=null
    var isijudul:Int?=null
    var jenis:String?=null
    var date= Timestamp(System.currentTimeMillis())
    var status=false
    var dwo:String=""
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var dbfirestore: FirebaseFirestore? =null
    private var filePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseStore= FirebaseStorage.getInstance()
        storageReference= firebaseStore!!.reference
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kosong)
        setSupportActionBar(findViewById(R.id.muki))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val spinner = spinner2
        val myStrings = arrayOf("Kategori Besi", "Kategori Plastik", "Kategori Elektronik", "Kategori Kertas")

        spinner.adapter = ArrayAdapter( this,
            android.R.layout.simple_expandable_list_item_1, myStrings)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val progress= ProgressDialog(this@Kosong)
                progress.setMessage("Memuat Data")
                progress.setCancelable(false)
                progress.show()
                kategori=myStrings[p2]
                if (kategori=="Kategori Besi"){
                    textView13.text="Besi"
                    judul= myStrings[p2]
                    dbfirestore= FirebaseFirestore.getInstance()
                    dbfirestore!!.collectionGroup("besi")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val spin = spinnerss
                                val mystring2= ArrayList<String>()
                                val mystring3= ArrayList<String>()


                                for (doc in task.result!!) {
                                    val note = doc.toObject<Post>(Post::class.java)
                                    note.id=doc.id
                                    note.Nama=doc.getString("Nama")
                                    mystring2.add(note.Nama!!)
                                    mystring3.add(note.harga.toString()!!)
                                    spin.adapter = ArrayAdapter( this@Kosong,
                                        android.R.layout.simple_expandable_list_item_1, mystring2)
                                    spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                        {  hargatetap.text="Rp "+mystring3[p2]+" Perkilo"
                                            textView22.text=mystring2[p2]
                                            jenis=textView22.text.toString()
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
                    if (kategori=="Kategori Plastik"){
                        judul=myStrings[p2]
                        dbfirestore= FirebaseFirestore.getInstance()
                        dbfirestore!!.collectionGroup("plastik")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val spin = spinnerss
                                    val mystring2= ArrayList<String>()
                                    val mystring3= ArrayList<String>()


                                    for (doc in task.result!!) {
                                        val note = doc.toObject<Post>(Post::class.java)
                                        note.id=doc.id
                                        note.Nama=doc.getString("Nama")
                                        mystring2.add(note.Nama!!)
                                        mystring3.add(note.harga.toString()!!)
                                        spin.adapter = ArrayAdapter( this@Kosong,
                                            android.R.layout.simple_expandable_list_item_1, mystring2)
                                        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                            {  hargatetap.text="Rp "+mystring3[p2]+" Perkilo"
                                                textView13.text="Plastik"
                                                textView22.text=mystring2[p2]
                                                isijudul=mystring3[p2].toInt()
                                                jenis=textView22.text.toString()
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
                    else{if (kategori=="Kategori Elektronik"){
                        textView13.text="Elektronik"
                        judul=myStrings[p2]
                        dbfirestore= FirebaseFirestore.getInstance()
                        dbfirestore!!.collectionGroup("elektronik")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val spin = spinnerss
                                    val mystring2= ArrayList<String>()
                                    val mystring3= ArrayList<String>()


                                    for (doc in task.result!!) {
                                        val note = doc.toObject<Post>(Post::class.java)
                                        note.id=doc.id
                                        note.Nama=doc.getString("Nama")
                                        mystring2.add(note.Nama!!)
                                        mystring3.add(note.harga.toString()!!)
                                        spin.adapter = ArrayAdapter( this@Kosong,
                                            android.R.layout.simple_expandable_list_item_1, mystring2)
                                        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                            { hargatetap.text="Rp "+mystring3[p2]+" PerUnit"
                                                isijudul=mystring3[p2].toInt()
                                                textView22.text=mystring2[p2]
                                                beratbarang.hint="Jumlah Unit"
                                                jenis=textView22.text.toString()
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
                        textView13.text="Kertas"
                        judul=myStrings[p2]
                        dbfirestore= FirebaseFirestore.getInstance()
                        dbfirestore!!.collectionGroup("kertas")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val spin = spinnerss
                                    val mystring2= ArrayList<String>()
                                    val mystring3= ArrayList<String>()


                                    for (doc in task.result!!) {
                                        val note = doc.toObject<Post>(Post::class.java)
                                        note.id=doc.id
                                        note.Nama=doc.getString("Nama")
                                        mystring2.add(note.Nama!!)
                                        mystring3.add(note.harga.toString()!!)
                                        spin.adapter = ArrayAdapter(this@Kosong,
                                            android.R.layout.simple_expandable_list_item_1, mystring2)
                                        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                                            {  hargatetap.text="Rp "+mystring3[p2]+" Perkilo"
                                                isijudul=mystring3[p2].toInt()
                                                textView22.text=mystring2[p2]
                                                jenis=textView22.text.toString()
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
        imageView7.setOnClickListener {
            val inten = Intent(Intent.ACTION_PICK)
            inten.type="image/*"
            startActivityForResult(inten,0)
        }
        button4.setOnClickListener {
            var  popmenu= PopupMenu(this,button4)
            popmenu.inflate(R.menu.validasi)
            popmenu.setOnMenuItemClickListener {item ->
                when(item.itemId){
                    R.id.lanjut-> {
                        val progress= ProgressDialog(this)
                        progress.setMessage("Menyimpan Data.....")
                        progress.setCancelable(false)
                        progress.show()
                        cek()
                        Handler().postDelayed({progress.dismiss()},500)
                    }
                    R.id.batal-> {

                    }
                }
                false
            }
            popmenu.show()
        }
    }
    private fun cek() {
        if (barangnama.text.isEmpty()){
            barangnama.error="Nama barang Tidak Boleh Kosong"
            Toast.makeText(this,"Error Masukan", Toast.LENGTH_LONG).show()
        } else{
            if (beratbarang.text.isEmpty()){
                beratbarang.error="Berat Tidak Boleh kosong"
                Toast.makeText(this,"Error Masukan", Toast.LENGTH_LONG).show()
            }else{
                if (deskripsibarang.text.isEmpty()){
                    deskripsibarang.error="Detail Barang harus di isi"
                    Toast.makeText(this,"Error Masukan", Toast.LENGTH_LONG).show()
                }else{
                    uploadimage()
                }
            }
        }
    }

    private fun getsetbarang(uId: String) {
        var namabarang=barangnama.text
        var berat:Number?=null
        var isideskripsi= deskripsibarang.text.toString()
        var hargabarng:Number?=null
        berat=beratbarang.text.toString().toFloat()
        hargabarng= isijudul?.times(berat)
        setbarang(uId, namabarang,judul,jenis, isideskripsi,berat!!,hargabarng!!,status,date)

    }

    private fun setbarang(
        uId: String,
        namabarang: Editable,
        judule: String?,
        jenise: String?,
        isideskrip: String,
        berat: Number,
        hargabarng: Number,
        status: Boolean,
        date: Timestamp
    )
    {  var kategorijenisutama=judule.toString()
        var kategorijenis=jenise.toString()
        var namadagangan= namabarang.toString()

        setdatajual(uId,namadagangan,kategorijenisutama,kategorijenis,isideskrip,berat,hargabarng,status,date)
    }

    private fun setdatajual(
        uId: String,
        namadagangan: String,
        judule: String,
        kategorijenis: String,
        isideskrip: String,
        berat: Number,
        hargabarng: Number,
        status: Boolean,
        date: Timestamp
    ) {
        var gambar =dwo
        val data = transaksi(namadagangan,berat,judule,kategorijenis,date,hargabarng,isideskrip,gambar,status).toMap()
        transaksijual.document(uId).collection("JualBarang").document("${UUID.randomUUID()}${uId}").set(data)
        val intent = Intent(this, kantong::class.java)
        startActivity(intent)
    }

    private fun uploadimage()  {
        val user = FirebaseAuth.getInstance().currentUser
        val progress=ProgressDialog(this)
        progress.setMessage("Menyimpan Data...")
        progress.setCancelable(false)
        progress.show()

        if (filePath != null) {
            val filename = UUID.randomUUID().toString()
            val ref = storageReference?.child("transaksi/$filename")

            ref!!.putFile(filePath!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        dwo=it.toString()
                        val ejo= user!!.uid
                        getdata(ejo)
                        getsetbarang(ejo)
                        progress.dismiss()

                    }
                }
                .addOnFailureListener {
                }
        }
        else{
            Toast.makeText(null,"Harap masukan gambar", Toast.LENGTH_LONG).show()
            progress.dismiss()
        }

    }

    private fun getdata(uId:String) {
        var name:String
        var hp :String
        var alamat:String
        dbfirestore= FirebaseFirestore.getInstance()
        dbfirestore!!.collection("Barkas").document("user")
            .collection("userid").document(uId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val notesList = mutableListOf<User>()
                    val note = document.toObject<User>(User::class.java)
                    note!!.uId=document.id
                    note.alamat=document.getString("Alamat")
                    note.name=document.getString("Nama")
                    note.hp=document.getString("NoHp")
                    notesList.add(note)
                    alamat=note.alamat.toString()
                    name= note.name.toString()
                    hp= note.hp.toString()
                    setdata(uId,name,hp,alamat)

                } else {

                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

    }

    private fun setdata(uId: String,
                        name: String,hp:String,alamat:String){
        //memasukan data ke database
        val data = User(name,hp,alamat).toMaps()
        transaksijual.document(uId).set(data)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....


            filePath= data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,filePath)
            imageView7.setImageBitmap(bitmap)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        onDestroy()
        finish()
    }
    override fun onBackPressed() {
        val intent = Intent(this, kantong::class.java)
        startActivity(intent)
                finish()
    }
}