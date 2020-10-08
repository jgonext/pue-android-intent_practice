package es.pue.intentpractice.presentationlayer.controllers.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intentpractice.R
import kotlinx.android.synthetic.main.activity_dialer_contacts.*

// TODO Consultar bbdd contactos + seleccion contacto + retorno de numero tlf
class DialerContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer_contacts)

        adc_btn.setOnClickListener(){
            val data = Intent()
            data.putExtra("MESSAGE", "HOLA!")
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}