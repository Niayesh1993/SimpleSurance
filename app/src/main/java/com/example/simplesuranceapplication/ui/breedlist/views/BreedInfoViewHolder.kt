package com.example.simplesuranceapplication.ui.breedlist.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesuranceapplication.databinding.ItemBreedInfoBinding

class BreedInfoViewHolder private constructor(private val binding: ItemBreedInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun createFrom(parent: ViewGroup): BreedInfoViewHolder {
            return BreedInfoViewHolder(
                ItemBreedInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun onBind(breedTitle: String, addFavoriteClickListener: (String) -> Unit, removeFavoriteClickListener: (String) -> Unit, showImageClickListener: (String) -> Unit) {
        binding.txtBreedTitle.text = breedTitle
        binding.btnShowImage.setOnClickListener { showImageClickListener(breedTitle) }
        binding.btnAddFavorite.setOnClickListener {
            binding.btnAddFavorite.visibility = View.GONE
            binding.btnRemoveFavorite.visibility = View.VISIBLE
            addFavoriteClickListener(breedTitle)
        }
        binding.btnRemoveFavorite.setOnClickListener {
            binding.btnAddFavorite.visibility = View.VISIBLE
            binding.btnRemoveFavorite.visibility = View.GONE
            removeFavoriteClickListener(breedTitle)
        }

    }

}