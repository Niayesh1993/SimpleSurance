package com.example.simplesuranceapplication.ui.imageslist.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesuranceapplication.databinding.ItemBreedImageBinding
import com.example.simplesuranceapplication.utils.bindImage

class BreedImageViewHolder private constructor(private val binding: ItemBreedImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun createFrom(parent: ViewGroup): BreedImageViewHolder {
            return BreedImageViewHolder(
                ItemBreedImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun onBind(imageUrl: String) {
        bindImage(
            imageUrl = imageUrl,
            imageView = binding.breedImage
        )
    }

}