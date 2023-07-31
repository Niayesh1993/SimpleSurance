package com.example.simplesuranceapplication.ui.imageslist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.simplesuranceapplication.ui.imageslist.views.BreedImageViewHolder


object BreedsImageDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class BreedsImageAdapter: ListAdapter<String, BreedImageViewHolder>(BreedsImageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedImageViewHolder {
        return BreedImageViewHolder.createFrom(parent)
    }

    override fun onBindViewHolder(holder: BreedImageViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}