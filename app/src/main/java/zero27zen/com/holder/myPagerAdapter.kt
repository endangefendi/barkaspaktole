package zero27zen.com.holder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import zero27zen.com.Log_belumseleai
import zero27zen.com.Log_selesai
import zero27zen.com.Riwayat

class myPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                    Log_belumseleai()
            }
            1->{
                Log_selesai()
            }
            else->{
                return Riwayat()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Belum Selesai"
            1 -> "Selesai"
            else -> "Riwayat"
        }
    }
}