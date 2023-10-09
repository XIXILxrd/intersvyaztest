package com.example.intersvyaztest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.itntersvyaztest.databinding.FragmentFavoriteCoinListBinding

class FavoriteCoinListFragment: Fragment() {

    private var _binding: FragmentFavoriteCoinListBinding? = null
    private val binding: FragmentFavoriteCoinListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteCoinListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun launchDetailFragment(fromSymbol: String) {
        findNavController().navigate(
            FavoriteCoinListFragmentDirections.actionFavoriteCoinListFragmentToCoinDetailFragment(
                fromSymbol
            )
        )
    }
}