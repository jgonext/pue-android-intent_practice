package es.pue.intentpractice.presentationlayer.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentpractice.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ac_btn_web.setOnClickListener(){ startActivity(Intent(this, WebActivity::class.java)) }
        ac_btn_gps.setOnClickListener(){ startActivity(Intent(this, GpsActivity::class.java)) }
        ac_btn_dialer.setOnClickListener(){ startActivity(Intent(this, DialerActivity::class.java)) }
    }

}