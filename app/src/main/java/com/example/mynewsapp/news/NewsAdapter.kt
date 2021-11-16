package com.example.mynewsapp.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynewsapp.R

class NewsAdapter (
    private val news: List<News>,
    private val rowLayout: Int,
    private val context: Context,
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var newsTitle: TextView = v.findViewById(R.id.title)
        internal var description: TextView = v.findViewById(R.id.description)
        val art: ImageView = v.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = news[position]
        holder.newsTitle.text = current.title
        holder.description.text = current.description
        val url = current.urlToImage
        Glide.with(context).load(url).into(holder.art)
        holder.itemView.setOnClickListener {
            val uri = Uri.parse(current.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}