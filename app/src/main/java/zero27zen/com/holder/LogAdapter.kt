package zero27zen.com.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import zero27zen.com.R
import zero27zen.com.model.log
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class LogAdapter(
    private val notesList: MutableList<log>,
    private val context: Context
)
    : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.log_fragment, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        val timestamp = note.date as com.google.firebase.Timestamp
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(milliseconds)
        val date = sdf.format(netDate).toString()
        if (note.statusjadi==true) {
            if (note.status==true) {
                holder.editvisibl()
            }else{
            if (note.kategori== "Kategori Elektronik"){
                holder.namaberat.text="Jumlah Unit"
            }else{
                holder.namaberat.text="Berat(KG)"
            }
                val currencyformay= NumberFormat.getCurrencyInstance()
                var ekoki= currencyformay.format(note.hargabarng)
            holder.jualan.text = note.uId
            holder.tanggal.text = date
            holder.jeniskategori.text = "${note.jenis} ${note.kategori}"
            holder.beratbarang.text = note.berat.toString()
            holder.namabarang.text = note.namabarang
            holder.hargabarang.text = ekoki
            Picasso.get().load(note.gbarang).into(holder.gbbarang)
        }}
        else{if (note.statusjadi== null){
            holder.editvisibl()
        }
            holder.editvisibl()
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun editvisibl(){
            itemView.visibility=View.GONE
        }

        internal var namaberat:TextView
        internal var tanggal:TextView
        internal var hargabarang: TextView
        internal var jualan:TextView
        internal var jeniskategori:TextView
        internal var namabarang: TextView
        internal var beratbarang:TextView
        internal var gbbarang:ImageView
        init {
            namaberat=itemview.findViewById(R.id.textView19)
            tanggal=itemview.findViewById(R.id.log_date)
            hargabarang=itemview.findViewById(R.id.hargajual)
            jeniskategori=itemview.findViewById(R.id.kategoridanjenis)
            jualan=itemview.findViewById(R.id.idjual)
            namabarang=itemview.findViewById(R.id.log_name)
            beratbarang=itemview.findViewById(R.id.log_weight)
            gbbarang=itemview.findViewById(R.id.log_mage)
        }
    }
}