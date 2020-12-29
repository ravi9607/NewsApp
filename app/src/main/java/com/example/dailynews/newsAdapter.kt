package com.example.dailynews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailynews.R.id.title

class newsAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<newsAdapter.NewViewHolder>() {
    private val items: ArrayList<News> = ArrayList()

    class NewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView =  itemView.findViewById(title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val descp: TextView = itemView.findViewById(R.id.decp)
        val date:TextView = itemView.findViewById(R.id.date)
        val author: TextView =  itemView.findViewById(R.id.author)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val viewHolder = NewViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        holder.descp.text=currentItem.description
        holder.date.text=currentItem.publisheddate
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }
    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

interface NewsItemClicked{

    fun onItemClicked(item: News)
}