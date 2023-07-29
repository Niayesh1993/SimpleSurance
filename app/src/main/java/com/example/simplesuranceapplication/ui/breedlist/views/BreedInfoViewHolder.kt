package com.example.simplesuranceapplication.ui.breedlist.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesuranceapplication.databinding.ItemBreedInfoBinding
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiModel

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

    fun onBind(breedUiModel: BreedUiModel, addFavoriteClickListener: (BreedUiModel) -> Unit, removeFavoriteClickListener: (BreedUiModel) -> Unit, showImageClickListener: (String) -> Unit) {
        binding.txtBreedTitle.text = breedUiModel.title
        if(breedUiModel.favorite){
            binding.btnAddFavorite.visibility = View.GONE
            binding.btnRemoveFavorite.visibility = View.VISIBLE
        }else {
            binding.btnAddFavorite.visibility = View.VISIBLE
            binding.btnRemoveFavorite.visibility = View.GONE
        }
        binding.btnShowImage.setOnClickListener { showImageClickListener(breedUiModel.title) }

        binding.btnAddFavorite.setOnClickListener {
            binding.btnAddFavorite.visibility = View.GONE
            binding.btnRemoveFavorite.visibility = View.VISIBLE
            breedUiModel.favorite = true
            addFavoriteClickListener(breedUiModel)
        }
        binding.btnRemoveFavorite.setOnClickListener {
            binding.btnAddFavorite.visibility = View.VISIBLE
            binding.btnRemoveFavorite.visibility = View.GONE
            breedUiModel.favorite = false
            removeFavoriteClickListener(breedUiModel)
        }

    }

}