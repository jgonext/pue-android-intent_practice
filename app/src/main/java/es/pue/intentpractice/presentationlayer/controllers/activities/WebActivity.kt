package es.pue.intentpractice.presentationlayer.controllers.activities

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intentpractice.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        //val escapedQuery: String = URLEncoder.encode(aw_txt.text.toString(), "UTF-8")
        //val uri: Uri = Uri.parse("https://www.google.com/search?q=$escapedQuery")

//        val start: (String) -> Unit = { pck ->
//            val uri: Uri = Uri.parse("http://" + aw_txt.text.toString())
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.setPackage(pck)
//            startActivity(intent)
//        }
//        aw_btn_browser.setOnClickListener(){ start("com.android.chrome") }
//        aw_btn_search.setOnClickListener(){ start("org.chromium.webview_shell") }

        aw_btn_browser.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + aw_txt.text.toString()))
            startActivity(intent)
        }

        aw_btn_search.setOnClickListener(){
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, aw_txt.text.toString())
            startActivity(intent)
        }

    }
}


