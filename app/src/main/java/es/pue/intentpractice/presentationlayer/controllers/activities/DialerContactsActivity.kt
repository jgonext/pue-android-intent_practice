package es.pue.intentpractice.presentationlayer.controllers.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.example.intentpractice.R
import kotlinx.android.synthetic.main.activity_dialer_contacts.*

class DialerContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer_contacts)
        load()
    }

    private fun load() {
        if (
            Build.VERSION.SDK_INT>= Build.VERSION_CODES.M &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        } else {
            loadContacts()
        }
    }

    private fun loadContacts() {
        val projection = null //arrayOf()
        val selection = "${ContactsContract.Contacts.HAS_PHONE_NUMBER} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME} Desc"
        val cursor: Cursor? = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, selection, selectionArgs, sortOrder)
        val sca = SimpleCursorAdapter( this, android.R.layout.simple_list_item_1, cursor, arrayOf(ContactsContract.Contacts.DISPLAY_NAME), intArrayOf(android.R.id.text1), 1)
        adc_list.adapter = sca
        adc_list.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id -> changeToDialer(id) }
    }

    private fun changeToDialer(id:Long) {
        val data = Intent()
        data.putExtra("MESSAGE", getPhoneFromContact(id))
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun getPhoneFromContact(id:Long): String? {
        var ret: String? = "";
        val projection =  arrayOf(Phone.NUMBER) //ContactsContract.CommonDataKinds.Phone.NUMBER
        val selection = "${Phone.CONTACT_ID} = ? AND ${Phone.TYPE} = ${Phone.TYPE_MOBILE}"
        val selectionArgs = arrayOf(id.toString())
        val sortOrder = null
        val cursor: Cursor? = contentResolver.query(Phone.CONTENT_URI, projection, selection, selectionArgs, sortOrder)
        cursor?.apply {
            if (moveToFirst()) ret = getString(getColumnIndex(Phone.NUMBER))
        }
        cursor?.close()
        return ret;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1->{
                if ( grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED ) {
                    loadContacts()
                } else {
                    Toast.makeText(this, "Falta aceptar permisos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}