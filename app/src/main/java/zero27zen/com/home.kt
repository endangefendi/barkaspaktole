package zero27zen.com

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.utama.view.*

class home : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.utama, container, false)
        view.ele.setOnClickListener{
            startActivity(Intent(context,elektronik::class.java))
        }
        view.ker.setOnClickListener{
            startActivity(Intent(context,kertas::class.java))
        }
        view.plas.setOnClickListener{
            startActivity(Intent(context,plastik::class.java))
        }
        view.bes.setOnClickListener{
            startActivity(Intent(context,besi::class.java))
        }
        return view
    }

}
