package es.pue.intentpractice.presentationlayer.controllers.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.FontsContract.Columns.RESULT_CODE
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.intentpractice.R
import kotlinx.android.synthetic.main.activity_dialer.*


class DialerActivity : AppCompatActivity(), View.OnClickListener {

    val SELECT_PHONE_NUMBER = 1
    val SELECT_PHONE_NUMBER2 = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer)
//        arrayOf(
//            ad_btn1, ad_btn2, ad_btn3,
//            ad_btn4, ad_btn5, ad_btn6,
//            ad_btn7, ad_btn8, ad_btn9,
//            ad_btnSearch, ad_btn0, ad_btnCall,
//            ad_btnReset
//        ).forEach { button -> button.setOnClickListener(this) }

        val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        getViewsByTag(viewGroup, "is_button")?.forEach { view -> if (view is Button) (view as Button).setOnClickListener(this) }

    }

    private fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View>? {
        val views = ArrayList<View>()
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) views.addAll(getViewsByTag(child, tag)!!)
            val tagObj = child.tag
            if (tagObj != null && tagObj == tag) views.add(child)
        }
        return views
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ad_btnSearch2 -> openContacts2()
            R.id.ad_btnSearch -> openContacts()
            R.id.ad_btnCall -> {
                val number = ad_text.text
                if (number.isNullOrEmpty()) {
                    Toast.makeText(this, "Falta el numero de telefono", Toast.LENGTH_LONG).show()
                } else {
                    //phoneDialer(number..toString())
                    phoneCall(number.toString())
                }
            }
            R.id.ad_btnReset -> ad_text.setText("")
            else -> ad_text.setText(ad_text.text.toString() + (v as Button).text)
        }
    }

    private fun openContacts2() {
        val i = Intent(this@DialerActivity, DialerContactsActivity::class.java)
        startActivityForResult(i, SELECT_PHONE_NUMBER2)
    }

    private fun openContacts() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(i, SELECT_PHONE_NUMBER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHONE_NUMBER2 && resultCode == Activity.RESULT_OK) {
            val message: String? = data?.getStringExtra("MESSAGE")
            Log.i("XXX", "message=$message")
            Toast.makeText(this, "message=$message", Toast.LENGTH_LONG).show()
        } else
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == Activity.RESULT_OK) {
            val contactUri: Uri? = data?.data
            val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            if (contactUri != null) {
                contentResolver.query(contactUri, projection, null, null, null).use { cursor ->
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            val numberIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val number = numberIndex?.let { cursor?.getString(it) }
                            ad_text.setText(""+number)
                        }
                    }
                }
            }
        }
    }

    private fun phoneDialer(number: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$number")
        startActivity(dialIntent)
    }
    private fun phoneCall(number: String) {
        val uri = Uri.parse("tel:$number")
        if (
            Build.VERSION.SDK_INT>= Build.VERSION_CODES.M &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        } else {
            startActivity(Intent(Intent.ACTION_CALL, uri))
        }
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
                    phoneCall(ad_text.text.toString())
                } else {
                    Toast.makeText(this, "Falta aceptar permisos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}