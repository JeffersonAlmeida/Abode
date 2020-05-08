package com.doublef.abode.features.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doublef.abode.model.Photo
import com.doublef.abode.R
import kotlinx.android.synthetic.main.activity_main_item.view.*

class Adapter(private var data: List<Photo>, private val listener: Listener): RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(photo: Photo){
            itemView.imageView.setImageURI(Uri.parse(photo.url))
            itemView.imageView.adjustViewBounds
            itemView.imageView.setOnClickListener{
                listener.onClickItem(photo)
            }
        }
    }

    fun changeData(newData: List<Photo>){
        val list = this.data as ArrayList
        list.clear()
        list.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.activity_main_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int  =  data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    interface Listener {
        fun onClickItem(photo: Photo)
    }
}