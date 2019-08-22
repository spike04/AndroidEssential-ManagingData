package com.rubin.datalearning.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rubin.datalearning.R
import com.rubin.datalearning.data.Monster
import kotlinx.android.synthetic.main.monster_grid_item.view.*

class MainRecyclerAdapter(private val context: Context, private val monsters: List<Monster>) :
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    override fun getItemCount() = monsters.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.monster_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monster = monsters[position]

        holder.run {
            nameText.let {
                it.text = monster.monsterName
                it.contentDescription = monster.description
            }
            ratingBar.rating = monster.scariness.toFloat()
            Glide.with(context).load(monster.thumbnailUrl).into(monsterImage)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.nameText
        val monsterImage: ImageView = itemView.monsterImage
        val ratingBar: RatingBar = itemView.ratingBar
    }
}