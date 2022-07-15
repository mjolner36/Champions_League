package com.example.championsleague

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.lifecycle.lifecycleScope
import com.example.championsleague.databinding.ActivityTestBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    var score = 0
    var mymatcheslist: List<Match>? = null
    var question:Int = 0
    var anim: Animation? = null

    var wrongSound: MediaPlayer? = null
    var correctSound: MediaPlayer? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(LayoutInflater.from(this@TestActivity))
        setContentView(binding.root)

        wrongSound = MediaPlayer.create(this,R.raw.wrong_sound)
        correctSound = MediaPlayer.create(this,R.raw.correct_sound)

        var vDBHlpr = DbHelper(this)
        mymatcheslist = vDBHlpr.allMatches as List<Match>

        setDataToView(question)


    }


    fun setDataToView( number:Int){
        if (question>=mymatcheslist!!.size){
           gameOver()
        } else {
        with(binding){
            Team1.text = mymatcheslist?.get(number)!!.team1
            Team2.text = mymatcheslist?.get(number)!!.team2

            Picasso.get()
                .load(mymatcheslist!![number].image1)
                .into(image1)
            Picasso.get()
                .load(mymatcheslist!![number].image2)
                .into(image2)

            yearText.text = "${mymatcheslist!![number].year} - ${mymatcheslist!![number].year.toInt() +1}"
            winnerTeam1.text = mymatcheslist!![number].team1
            winnerTeam2.text = mymatcheslist!![number].team2
            }
        }
    }

    fun onClick(view: View){
        var answerText:TextView = findViewById(R.id.answerText);
        anim = AnimationUtils.loadAnimation(this,R.anim.myalpha);
        if ((mymatcheslist!![question].winner == "1" && view.id == R.id.winnerTeam1) ||
            (mymatcheslist!![question].winner == "2" && view.id == R.id.winnerTeam2) )
        {
            correctSound?.start()
            answerText.text = "Correct:)"
            score+=1
            answerText.setTextColor(resources.getColor(R.color.tertiary))
        }
        else {
            wrongSound?.start()
            answerText.text = "Wrong:("
            answerText.setTextColor(resources.getColor(R.color.red))
        }
        lifecycleScope.launch(){
            answerText.startAnimation(anim)
            delay(1500L)
            question+=1
            setDataToView(question)
        }
    }

    private fun gameOver(){
        var answerText:TextView = findViewById(R.id.answerText);
        anim = AnimationUtils.loadAnimation(this,R.anim.myalpha);
        answerText.setTextColor(resources.getColor(R.color.tertiary))
        answerText.text = "Your score: $score/${mymatcheslist!!.size}"

        lifecycleScope.launch(){
            answerText.startAnimation(anim)
            answerText.visibility = View.VISIBLE
            delay(4500L)
            var intent = Intent(this@TestActivity,MainActivity::class.java)
            startActivity(intent)
        }

    }

}