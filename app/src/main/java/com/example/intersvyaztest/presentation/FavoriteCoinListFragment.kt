package com.example.intersvyaztest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.presentation.adapter.CoinInfoAdapter
import com.example.itntersvyaztest.databinding.FragmentFavoriteCoinListBinding

class FavoriteCoinListFragment: Fragment() {
    private lateinit var viewModel: CoinViewModel

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

        val adapter = CoinInfoAdapter(requireActivity().application)

        bindRecyclerView(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun bindRecyclerView(adapter: CoinInfoAdapter) {
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                launchDetailFragment(coinPriceInfo.fromSymbol)
            }
        }

        binding.favoriteCoinListRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getFavoriteCoins().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailFragment(fromSymbol: String) {
        findNavController().navigate(
            FavoriteCoinListFragmentDirections.actionFavoriteCoinListFragmentToCoinDetailFragment(
                fromSymbol
            )
        )
    }
}