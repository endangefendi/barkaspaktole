package zero27zen.com.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import zero27zen.com.R
import zero27zen.com.model.Post

class NoteRecyclerViewAdapter(
    private val notesList: MutableList<Post>,
    private val context: Context)
    : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.isi_harga, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.Nama.text = note.Nama
        holder.harga.text = note.harga.toString()
        Picasso.get().load(note.gambar).into(holder.image)


    }




    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var Nama :TextView
        internal var harga: TextView
        internal var image: ImageView


        init {
            image=view.findViewById(R.id.isiemage5)
            Nama = view.findViewById(R.id.Uangngalir)
            harga = view.findViewById(R.id.hargaisi)

        }
    }
}