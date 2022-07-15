package com.example.championsleague

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View){
        when(view.id){
            R.id.privacy -> {
                val url = "https://www.google.com/"
                val customTabsIntent: CustomTabsIntent = CustomTabsIntent.Builder().setColorScheme(
                    CustomTabsIntent.COLOR_SCHEME_DARK).build()
                customTabsIntent.launchUrl(this, Uri.parse(url));
            }
            R.id.LeaguesInfo ->{
                var intent = Intent(this@MainActivity,MatchesActivity::class.java)
                startActivity(intent)
            }
            R.id.TestInfo -> {
                var intent = Intent(this@MainActivity,TestActivity::class.java)
                startActivity(intent)
            }
        }
    }
}