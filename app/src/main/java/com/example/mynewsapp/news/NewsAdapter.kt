package com.example.mynewsapp.news

import android.content.Context
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
    private val clickOnNews: (News) -> Unit,
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var newsTitle: TextView = v.findViewById(R.id.title)
        internal var description: TextView = v.findViewById(R.id.description)
        internal var newsLayout: ViewGroup= v.findViewById(R.id.news_layout)
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
        holder.newsLayout.setOnClickListener{clickOnNews.invoke(current)}
    }

    override fun getItemCount(): Int {
        return news.size
    }
}