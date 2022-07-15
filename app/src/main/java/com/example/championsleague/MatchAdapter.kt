package com.example.championsleague

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.championsleague.databinding.ItemMatchCardBinding
import com.squareup.picasso.Picasso
import java.sql.SQLData


class MatchAdapter(
    private val db: List<Match>

): RecyclerView.Adapter<MatchAdapter.MatchHolder>() {

    class MatchHolder(item: View):RecyclerView.ViewHolder(item) {
       private val binding = ItemMatchCardBinding.bind(item)
        fun bind(match:Match) =with(binding) {
            team1TextView.text = match.team1
            team2TextView.text = match.team2
            yearTextView.text = "${match.year} - ${match.year.toInt() +1}"
            score.text = match.score1 + "-" + match.score2

            Picasso.get()
                .load(match.image1)
                .into(team1ImageView)
            Picasso.get()
                .load(match.image2)
                .into(team2ImageView)

            winnerTextView.text = if (match.winner == "1")
               "Winner: ${match.team1}"
            else  "Winner: ${match.team2}"

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match_card,parent,false)
        return MatchHolder(view)
    }

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
       holder.bind(db[position])
    }

    override fun getItemCount(): Int {
        return db.size
    }
}