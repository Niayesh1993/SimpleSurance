package com.example.simplesuranceapplication.ui.breedlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplesuranceapplication.R
import com.example.simplesuranceapplication.databinding.FragmentFirstBinding
import com.example.simplesuranceapplication.ui.breedlist.adapter.BreedsInfoAdapter
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiModel
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiState
import com.zohre.domain.model.Breed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedsListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : BreedViewModel

    private lateinit var breedAdapter : BreedsInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BreedViewModel::class.java]
        initializeInfoList()
        initializeVenueObservers()

        binding.showFavoriteBreeds.setOnClickListener {
            binding.showAllBreeds.visibility = View.VISIBLE
            binding.showFavoriteBreeds.visibility = View.INVISIBLE
            showFavoriteBreeds()
        }

        binding.showAllBreeds.setOnClickListener {
            binding.showAllBreeds.visibility = View.INVISIBLE
            binding.showFavoriteBreeds.visibility = View.VISIBLE
            initializeVenueObservers()
        }

    }

    private fun showFavoriteBreeds() {
        breedAdapter.submitList(viewModel.favoriteBreedList)
    }

    private fun initializeVenueObservers() {
        lifecycleScope.launch {
            viewModel.breedState.collect{
                when(it){
                    BreedUiState.Loading -> showLoading()
                    is BreedUiState.BreedAvailable -> showBreedList(it.breedUiModels)
                    is BreedUiState.Failure -> hideLoading()
                }
            }
        }
    }

    private fun showBreedList(breedUiModels: List<BreedUiModel>) {
        hideLoading()
        breedAdapter.submitList(breedUiModels)
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun initializeInfoList() {
        breedAdapter = BreedsInfoAdapter(
            {viewModel.addBreedToFavorite(it)},
            {viewModel.removeBreedFromFavorite(it)},
            {
                val bundle = bundleOf("breedTitle" to it)
                findNavController().navigate(R.id.action_BreedFragment_to_FavoriteFragment, bundle)
            }
        )
        binding.cvBreedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cvBreedList.adapter = breedAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}