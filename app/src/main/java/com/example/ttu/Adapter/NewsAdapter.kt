package com.example.ttu.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ttu.R
import com.example.ttu.databinding.NewsBinding
import com.example.ttu.model.News
import com.squareup.picasso.Picasso

class NewsAdapter:ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtil()){
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = NewsBinding.bind(itemView)

        fun bind(news: News){
            Picasso.get().load(news.image_url).into(binding.image)
            binding.title.text = news.title
            binding.body.text = news.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news,parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class NewsDiffUtil: DiffUtil.ItemCallback<News>(){

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

}


















