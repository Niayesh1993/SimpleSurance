package com.example.simplesuranceapplication.ui.breedlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiModel
import com.example.simplesuranceapplication.ui.breedlist.views.BreedInfoViewHolder


object BreedsInfoDiffCallback : DiffUtil.ItemCallback<BreedUiModel>() {
    override fun areItemsTheSame(oldItem: BreedUiModel, newItem: BreedUiModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: BreedUiModel, newItem: BreedUiModel): Boolean {
        return oldItem.title == newItem.title
    }

}

class BreedsInfoAdapter(
    private val addFavoriteClickListener: (BreedUiModel) -> Unit,
    private val removeFavoriteClickListener: (BreedUiModel) -> Unit,
    private val showImageClickListener: (String) -> Unit) :
    ListAdapter<BreedUiModel, BreedInfoViewHolder>(BreedsInfoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedInfoViewHolder {
        return BreedInfoViewHolder.createFrom(parent)
    }

    override fun onBindViewHolder(holder: BreedInfoViewHolder, position: Int) {
        holder.onBind(getItem(position), addFavoriteClickListener, removeFavoriteClickListener, showImageClickListener)
    }

}