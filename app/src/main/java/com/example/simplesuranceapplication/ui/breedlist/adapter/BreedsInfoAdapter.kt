package com.example.simplesuranceapplication.ui.breedlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.simplesuranceapplication.ui.breedlist.views.BreedInfoViewHolder


object BreedsInfoDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

class BreedsInfoAdapter(
    private val addFavoriteClickListener: (String) -> Unit,
    private val removeFavoriteClickListener: (String) -> Unit,
    private val showImageClickListener: (String) -> Unit) :
    ListAdapter<String, BreedInfoViewHolder>(BreedsInfoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedInfoViewHolder {
        return BreedInfoViewHolder.createFrom(parent)
    }

    override fun onBindViewHolder(holder: BreedInfoViewHolder, position: Int) {
        holder.onBind(getItem(position), addFavoriteClickListener, removeFavoriteClickListener, showImageClickListener)
    }

}