package com.example.currency.view.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.data.models.gallery.Photo
import com.example.currency.databinding.ImageItemBinding


class ImagesAdapter (var list :ArrayList<Photo>): RecyclerView.Adapter<ImageViewHolder>() {
    private lateinit var binding: ImageItemBinding
    private var isLoadingAdded = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return   ImageViewHolder(binding)
    }
    fun setPhotoList(list: ArrayList<Photo>) {
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val photo = list[position]
        holder.bind(photo)
    }


    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = list.size - 1
        val result = getItem(position)
        if (result != null) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(photo: Photo?) {
        if (photo != null) {
            list.add(photo)
        }
        notifyItemInserted(list.size - 1)
    }

    fun addAll(moveResults: List<Photo?>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun getItem(position: Int): Photo? {
        return list.get(position)
    }
    fun add_photo(photoslist: List<Photo>) {
        for (photo in photoslist) {
            list.add(photo)
        }
        notifyDataSetChanged()
    }
}