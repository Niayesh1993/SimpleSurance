package com.example.simplesuranceapplication.ui.imageslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplesuranceapplication.R
import com.example.simplesuranceapplication.databinding.FragmentSecondBinding
import com.example.simplesuranceapplication.ui.imageslist.adapter.BreedsImageAdapter
import com.example.simplesuranceapplication.ui.imageslist.states.BreedImageUiState
import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedImagesListFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : BreedImageViewModel

    private lateinit var imageAdapter : BreedsImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("breedTitle")
        viewModel = ViewModelProvider(this)[BreedImageViewModel::class.java]

        initializeInfoList()
        initializeVenueObservers()
        viewModel.loadBreedImages(title)

        binding.txtTitle.text = title
    }

    private fun initializeVenueObservers() {
        lifecycleScope.launch {
            viewModel.breedImageState.collect{
                when(it){
                    BreedImageUiState.Loading -> showLoading()
                    is BreedImageUiState.BreedImagesAvailable -> showImageList(it.breedImages)
                    is BreedImageUiState.Failure -> hideLoading()
                }
            }
        }
    }

    private fun showImageList(images: BreedImages?) {
        hideLoading()
        imageAdapter.submitList(images!!.breedImages)
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun initializeInfoList() {
        imageAdapter = BreedsImageAdapter()
        binding.cvBreedImages.layoutManager = GridLayoutManager(requireContext(), 2 )
        binding.cvBreedImages.adapter = imageAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}