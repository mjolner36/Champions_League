package com.example.championsleague

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.sql.SQLException


class MatchesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)
        var vDBHlpr = DbHelper(this)
        val mymatcheslist: List<Match> = vDBHlpr.allMatches as List<Match>

        var rcView = findViewById<RecyclerView>(R.id.rcView)
        rcView.layoutManager = LinearLayoutManager(this@MatchesActivity)
        rcView.adapter = MatchAdapter(mymatcheslist)
    }


    fun onClick(view: View){
        val infoLayout = findViewById<ConstraintLayout>(R.id.information)
        if (infoLayout.visibility == View.GONE){
            infoLayout.visibility = View.VISIBLE
        }
        else infoLayout.visibility = View.GONE
    }
}