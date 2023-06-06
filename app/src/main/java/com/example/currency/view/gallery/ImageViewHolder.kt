package com.example.currency.view.gallery

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.gallery.Photo
import com.example.currency.databinding.ImageItemBinding


class ImageViewHolder (private val binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(photo: Photo) {
        binding.photo = photo
        loadImage(binding.img,photo.src.original)
        //Toast.makeText(binding.root.context,photo.src.original,Toast.LENGTH_LONG).show()
        //GlideApp.with(binding.root.context).load(photo.src.original).into(binding.img)
    }
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String) {
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }
}