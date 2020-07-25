package zero27zen.com


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.log.*
import zero27zen.com.holder.myPagerAdapter


class transaksi: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log)
        setSupportActionBar(findViewById(R.id.mantapu))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fragmentadapter=myPagerAdapter(supportFragmentManager)
        pagger.adapter=fragmentadapter
        tool.setupWithViewPager(pagger)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        finish()
    }

}
