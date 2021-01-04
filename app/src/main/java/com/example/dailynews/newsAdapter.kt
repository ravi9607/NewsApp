package com.example.dailynews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailynews.R.id.shareNews
import com.example.dailynews.R.id.title
import kotlinx.android.synthetic.main.item_view.view.*

class newsAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<newsAdapter.NewViewHolder>() {
    private val items: ArrayList<News> = ArrayList()
    var check=false

    class NewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView =  itemView.findViewById(title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val descp: TextView = itemView.findViewById(R.id.decp)
        val date:TextView = itemView.findViewById(R.id.date)
        val author: TextView =  itemView.findViewById(R.id.author)
        val share: ImageView=itemView.findViewById(shareNews)
        val like: ImageView=itemView.findViewById(R.id.like)

    }
    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        //val view1 = LayoutInflater.from(parent.context).inflate(shareNews,parent,false)
        val viewHolder = NewViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        view.shareNews.setOnClickListener{
            listener.onShareClick(items[viewHolder.adapterPosition])
        }
        view.like.setOnClickListener{
            listener.onlikeNews(items[viewHolder.adapterPosition])
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

        holder.like.setOnClickListener {

            if (!check){
                holder.like.setImageDrawable(ContextCompat.getDrawable(holder.like.context,R.drawable.like1))
                check=true
            }else{
                holder.like.setImageDrawable(ContextCompat.getDrawable(holder.like.context,R.drawable.like))
                check=false
            }
        }



    }
    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

interface NewsItemClicked{
    fun onShareClick(item: News)
    fun onItemClicked(item: News)
    fun onlikeNews(item: News)
}