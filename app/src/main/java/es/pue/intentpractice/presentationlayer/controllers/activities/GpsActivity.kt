package es.pue.intentpractice.presentationlayer.controllers.activities

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.intentpractice.R
import kotlinx.android.synthetic.main.activity_gps.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web.*
import java.net.URLEncoder


class GpsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        ag_btn_find.setOnClickListener(){
            val query: String = Uri.encode(ag_txt.text.toString())
            val uri = Uri.parse("geo:41.387295, 2.190003?q=$query")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            //intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

    }
}