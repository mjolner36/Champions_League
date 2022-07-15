package com.example.championsleague

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        lifecycleScope.launch(){
            delay(3000L)
            val intent: Intent = Intent(this@LoadingActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}