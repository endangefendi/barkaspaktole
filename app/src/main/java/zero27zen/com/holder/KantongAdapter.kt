package zero27zen.com.holder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import zero27zen.com.*
import zero27zen.com.model.isi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class KantongAdapter  (
    private val notesList: MutableList<isi>,
    private val context: Context)
    : RecyclerView.Adapter<KantongAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.fragmenttransaksi, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        val currencyformay=NumberFormat.getCurrencyInstance()
        var ekoki= currencyformay.format(note.hargabarng)
        if (note.statusjadi==false){
            val timestamp = note.date as com.google.firebase.Timestamp
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(milliseconds)
        val date = sdf.format(netDate).toString()
        holder.Nama.text=note.namabarang
        holder.kategori.text=note.kategori
        holder.berat.text=note.berat.toString()
        holder.jenis.text=note.jenis
        holder.tanggal.text= date
        holder.harga.text= ekoki.toString()
        holder.keterangan.text=note.keterangan
        if (note.gbarang!=null) {
            Picasso.get().load(note.gbarang).resize(50, 50)
                .centerCrop().into(holder.image);
        }else{
            Picasso.get().load(R.drawable.illustrasi_).resize(50, 50)
                .centerCrop().into(holder.image);
        }
        if (note.kategori.equals("Kategori Elektronik"))
        {
            holder.number2.text="Unit"
        }
        else{
            holder.number2.text="Berat"
        }
            holder.btnedit.setOnClickListener {
            edit(note.uId,holder.Nama,holder.berat,holder.kategori,holder.jenis,holder.keterangan,holder.harga,note.gbarang)
        }
            holder.btnhapus.setOnClickListener {
                holder.hapus(note.uId)
            }
            holder.btnpost.setOnClickListener {
               holder.hasilku(note.uId)
            }}else{
            holder.editvisibl()

                    }

    }




    private fun edit(
        uId: String?,
        nama: TextView,
        berat: TextView,
        kategori: TextView,
        jenis: TextView,
        keterangan: TextView,
        harga: TextView,
        gbarang: String?
    ) {

        val bundle = Bundle()
        bundle.putString("uid",uId)
        bundle.putString("nama",nama.text.toString())
        bundle.putString("berat",berat.text.toString())
        bundle.putString("kategori",kategori.text.toString())
        bundle.putString("jenis",jenis.text.toString())
        bundle.putString("keterangan",keterangan.text.toString())
        bundle.putString("gb",gbarang)
        val intent = Intent(context, Edit::class.java)
        intent.putExtras(bundle)
        startActivity(context,intent,null)

    }


    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(itemview: View) : RecyclerView.ViewHolder(itemview) {
        internal var isi:LinearLayout
        internal var Nama :TextView
        internal var harga: TextView
        internal var number2:TextView
        internal var keterangan: TextView
        internal var tanggal:TextView
        internal var berat:TextView
        internal var kategori:TextView
        internal var jenis: TextView
        internal var btnedit: Button
        internal var btnhapus: Button
        internal  var btnpost: Button
        internal var image: ImageView
        internal var satu :Int = 0


        init {
            isi=itemview.findViewById(R.id.semua)
            number2=itemview.findViewById(R.id.number2)
            image=itemview.findViewById(R.id.imageView)
            Nama = itemview.findViewById(R.id.number4)
            berat=itemview.findViewById(R.id.number5)
            harga = itemview.findViewById(R.id.number6)
            keterangan=itemview.findViewById(R.id.number8)
            tanggal=itemview.findViewById(R.id.Dates)
            jenis=itemview.findViewById(R.id.jenis2)
            kategori=itemview.findViewById(R.id.kategori2)
            btnedit=itemview.findViewById(R.id.button2) as Button
            btnhapus=itemview.findViewById(R.id.Batal1) as Button
            btnpost=itemview.findViewById(R.id.button)  as Button
            btnedit.setTag(R.integer.btnEdit,itemview)
            btnhapus.setTag(R.integer.btnHapus, itemview)
            btnpost.setTag(R.integer.btnPost, itemview)
    }
        fun hasilku(uId: String?) {
            val auth= FirebaseAuth.getInstance().currentUser
            var eko =FirebaseFirestore.getInstance()
            Toast.makeText(context,"Barang anda Sedang DI Proses Mohon Datang Ke PT Barkas PakTole terdekat",Toast.LENGTH_LONG).show()
            eko.collection("Barkas")
                .document("transaksi").collection("isi").document(auth!!.uid)
                .collection("JualBarang").document(uId.toString())
                .update("statusjadi",true)
            AlertDialog.Builder(itemView.context)
                .setMessage("Barang Telah Di terbitkan.\n " +
                        "Di harapkan user datang Ke outlet   Barkas Paktole Terdekat \n " +
                        "Guna Menyelesaikan Proses Penjualan Barkas.\n Hormat Kami Admin Barkas Paktole ")
                .show()

        }
        fun editvisibl(){
        itemView.visibility=View.GONE
        }
        fun hapus(uId: String?) {
            AlertDialog.Builder(itemView.context)
                .setMessage("Anda Yakin Akan Menghapus Data Ini?")
                .setPositiveButton(android.R.string.yes) { dialog, whichButton ->
                    val auth= FirebaseAuth.getInstance().currentUser
                    var eko =FirebaseFirestore.getInstance()
                    eko.collection("Barkas")
                        .document("transaksi").collection("isi").document(auth!!.uid)
                        .collection("JualBarang").document(uId.toString()).delete()
                    Toast.makeText(context,"Data berhasil Dihapus",Toast.LENGTH_LONG).show()

                }
                .setNegativeButton(android.R.string.no) { dialog, whichButton ->

                }
                .show()

        }

    }
}


